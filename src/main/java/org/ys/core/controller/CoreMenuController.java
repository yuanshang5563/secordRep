package org.ys.core.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.ys.common.constant.CoreMenuTypeContant;
import org.ys.common.constant.RedisKeyContant;
import org.ys.common.domain.Tree;
import org.ys.common.page.PageBean;
import org.ys.common.utils.DateTimeConverter;
import org.ys.common.utils.RequsetUtils;
import org.ys.core.model.CoreMenu;
import org.ys.core.model.CoreMenuExample;
import org.ys.core.model.CoreMenuExample.Criteria;
import org.ys.core.model.CoreRole;
import org.ys.core.model.CoreRoleExample;
import org.ys.core.model.CoreRoleMenu;
import org.ys.core.service.CoreMenuService;
import org.ys.core.service.CoreRoleMenuService;
import org.ys.core.service.CoreRoleService;
import org.ys.security.RequiredPermission;

@Controller
@RequestMapping("/manager/core/CoreMenuController")
public class CoreMenuController {
	@Autowired
	private CoreMenuService coreMenuService;
	@Autowired
	private CoreRoleService coreRoleService;
	@Autowired
	private CoreRoleMenuService coreRoleMenuService;		
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;	
	@SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate ;
	
	@RequiredPermission(permissionName="列表页面",permission="ROLE_CORE_MENU_LIST_PAGE")
	@RequestMapping("/coreMenuList")
	public ModelAndView coreMenuList() throws Exception {
		ModelAndView model = new ModelAndView("/manager/core_menu/core_menu_list");
		return model;
	}
	
	@RequiredPermission(permissionName="新增和修改页面",permission="ROLE_CORE_MENU_ADD_EDIT_PAGE")
	@RequestMapping("/coreMenuForm")
	public ModelAndView coreMenuForm(Long coreMenuId,String actionType) throws Exception {
		CoreMenu coreMenu = null;
		String parentMenuName = "根目录";
		if(StringUtils.equals("edit", actionType.trim()) || StringUtils.equals("view", actionType.trim())) {
			coreMenu = coreMenuService.queryCoreMenuById(coreMenuId);
		}else if(StringUtils.equals("addSub", actionType.trim())){
			coreMenu = new CoreMenu();
			coreMenu.setParentCoreMenuId(coreMenuId);
		}else {
			coreMenu = new CoreMenu();
		}
		if(null != coreMenu.getParentCoreMenuId() && coreMenu.getParentCoreMenuId() != 0) {
			CoreMenu parentMenu = coreMenuService.queryCoreMenuById(coreMenu.getParentCoreMenuId());
			if(null != parentMenu) {
				parentMenuName = parentMenu.getMenuName();
			}
		}
		ModelAndView model = new ModelAndView("/manager/core_menu/core_menu_form");
		model.addObject("parentMenuName", parentMenuName);
		model.addObject("coreMenu", coreMenu);
		model.addObject("actionType", actionType);
		return model;
	}
	
	@RequiredPermission(permissionName="新增和修改",permission="ROLE_CORE_MENU_ADD_EDIT")
	@RequestMapping("/saveCoreMenuForm")
	@ResponseBody
	public Map<String,Object> saveCoreMenuForm(HttpServletRequest request)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			Map<String,Object> paramMap = RequsetUtils.getParamsMap(request);
			String coreMenuId = request.getParameter("coreMenuId");
			CoreMenu coreMenu = null;
			if(StringUtils.isNotEmpty(coreMenuId)) {
				coreMenu = coreMenuService.queryCoreMenuById(Long.parseLong(coreMenuId));
			}else {
				coreMenu = new CoreMenu();
			}
			BeanUtilsBean.getInstance().getConvertUtils().register(new DateTimeConverter(), Date.class);
			BeanUtils.populate(coreMenu, paramMap);
			if(null == coreMenu.getCoreMenuId() || coreMenu.getCoreMenuId() == 0) {
				coreMenuService.save(coreMenu);
			}else {
				coreMenuService.updateById(coreMenu);
			}
			msg = "操作菜单成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,操作菜单失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	}  
	
	@RequiredPermission(permissionName="删除",permission="ROLE_CORE_MENU_DEL")
	@RequestMapping("/deleteCoreMenu")
	@ResponseBody
	public Map<String,Object> deleteCoreMenu(Long coreMenuId)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			coreMenuService.delCoreMenuById(coreMenuId);
			msg = "删除菜单成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,删除菜单失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	} 
	
	@RequestMapping("/ajaxFindCoreMenuById")
	@ResponseBody
	public Map<String,Object> ajaxFindCoreMenuById(Long coreMenuId)throws Exception {
		String msg = "";
		boolean success = false;
		CoreMenu menu = null;
		try {
			menu = coreMenuService.queryCoreMenuById(coreMenuId);
			msg = "查找菜单成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,查找菜单失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(success){
			map.put("data", menu);
		}else{
			map.put("errorMessage", msg);
		}
		map.put("success", success);
		return map;
	} 
	
	@RequiredPermission(permissionName="列表数据1",permission="ROLE_CORE_MENU_LIST1")
	@RequestMapping("/coreMenuListJsonData")
	@ResponseBody
	public Map<String,Object> coreMenuListJsonData(HttpServletRequest request)throws Exception {
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String menuName = request.getParameter("menuName");
		String menuType = request.getParameter("menuType");
		CoreMenuExample example = new CoreMenuExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(menuName)){
			criteria.andMenuNameLike(menuName.trim()+"%");
		}
		if(StringUtils.isNotEmpty(menuType)){
			criteria.andMenuTypeEqualTo(menuType.trim());
		}
		if(StringUtils.isEmpty(start) || StringUtils.equals(start, "0")) {
			start = "1";
		}
		if(StringUtils.isEmpty(limit)) {
			limit = "10";
		}
		List<CoreMenu> menus = null;
		long count = 0;
		PageBean<CoreMenu> pageBean = coreMenuService.pageCoreMenusByExample(example, Integer.parseInt(start.trim()), Integer.parseInt(limit.trim()));
		if(null != pageBean) {
			menus = pageBean.getList();
			count = pageBean.getTotal();
		}else {
			menus = new ArrayList<CoreMenu>();
		}
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("count", count);
		maps.put("root", menus);
		return maps;
	}	
	
	@RequiredPermission(permissionName="列表数据2",permission="ROLE_CORE_MENU_LIST2")
	@RequestMapping("/coreMenuListJsonDataNoPage")
	@ResponseBody
	public List<CoreMenu> coreMenuListJsonDataNoPage(HttpServletRequest request)throws Exception {
		CoreMenuExample example = new CoreMenuExample();
		Criteria criteria = example.createCriteria();
		criteria.andCoreMenuIdNotEqualTo(0l);
		List<CoreMenu> menuList = coreMenuService.queryCoreMenusByExample(example);
		if(null == menuList) {
			menuList = new ArrayList<CoreMenu>();
		}
		return menuList;
	}	
	
	@SuppressWarnings("unchecked")
	@RequiredPermission(permissionName="加载权限",permission="ROLE_CORE_MENU_LOAD_PERMISSIONS")
	@RequestMapping("/reLoadPermissions")
	@ResponseBody
	public Map<String,Object> reLoadPermissions() {
		String msg = "";
		boolean success = false;
		try {
			Map<HandlerMethod,String> methodAndUrlMap = new HashMap<HandlerMethod,String>();
			Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
			Set<RequestMappingInfo> mappingInfoSet = handlerMethodMap.keySet();
			for (RequestMappingInfo requestMappingInfo : mappingInfoSet) {
				PatternsRequestCondition patternsRequestCondition = requestMappingInfo.getPatternsCondition();
				Set<String> patternsSet = patternsRequestCondition.getPatterns();
				if(null != patternsSet && patternsSet.size() > 0) {
					methodAndUrlMap.put(handlerMethodMap.get(requestMappingInfo), patternsSet.iterator().next());
				}
			}
			Collection<HandlerMethod> handlerMethods = handlerMethodMap.values();
			//一次找出所有菜单权限
			Map<String,CoreMenu> existParentMenu = new HashMap<String,CoreMenu>();
			CoreMenuExample example = new CoreMenuExample();
			example.createCriteria().andMenuTypeEqualTo(CoreMenuTypeContant.MENU_TYPE_MENU);
			List<CoreMenu> parentMenuList = coreMenuService.queryCoreMenusByExample(example);
			if(null != parentMenuList && parentMenuList.size() > 0) {
				for (CoreMenu parentMenu : parentMenuList) {
					String menuUrl = parentMenu.getMenuUrl();
					if(StringUtils.isNotEmpty(menuUrl) && menuUrl.contains("List")) {
						int position = StringUtils.lastIndexOf(menuUrl, "/");
						String menuActionUrl = StringUtils.substring(menuUrl, 0, position);
						existParentMenu.put(menuActionUrl, parentMenu);
					}
				}
			}
			//找到所有权限
			Map<String,CoreMenu> existPermissions = new HashMap<String,CoreMenu>();
			example.clear();
			example.createCriteria().andMenuTypeEqualTo(CoreMenuTypeContant.MENU_TYPE_PERMISSION);
			List<CoreMenu> buttonList = coreMenuService.queryCoreMenusByExample(example);
			if(null != buttonList && buttonList.size() > 0) {
				for (CoreMenu button : buttonList) {
					existPermissions.put(button.getMenuUrl(), button);
				}
			}
			
			for (HandlerMethod handlerMethod : handlerMethods) {
				RequiredPermission requiresPermissionsAnno = handlerMethod.getMethodAnnotation(RequiredPermission.class);
				if(null == requiresPermissionsAnno) {
					continue;
				}
				String permission = requiresPermissionsAnno.permission();
				String permissionName = requiresPermissionsAnno.permissionName();
				if(StringUtils.isNotEmpty(permission)&&StringUtils.isNotEmpty(permissionName)) {
					String menuUrl = methodAndUrlMap.get(handlerMethod);
					int position = StringUtils.lastIndexOf(menuUrl, "/");
					String menuActionUrl = StringUtils.substring(menuUrl, 0, position);
					//先找父类
					CoreMenu parentMenu = null;
					if(existParentMenu.containsKey(menuActionUrl)) {
						parentMenu = existParentMenu.get(menuActionUrl);
					}
					if(null != parentMenu) {
						CoreMenu buttonMenu = null;
						if(existPermissions.containsKey(menuUrl)) {
							buttonMenu = existPermissions.get(menuUrl);
						}else {
							buttonMenu = new CoreMenu();
							buttonMenu.setMenuUrl(menuUrl);
							buttonMenu.setParentCoreMenuId(parentMenu.getCoreMenuId());
						}
						buttonMenu.setPermission(permission);
						buttonMenu.setMenuType(CoreMenuTypeContant.MENU_TYPE_PERMISSION);
						buttonMenu.setMenuName(permissionName);
						if(permission.contains("LIST")) {
							buttonMenu.setOrderNum(0);
						}else if(permission.contains("ADD")) {
							buttonMenu.setOrderNum(1);
						}else if(permission.contains("DEL")) {
							buttonMenu.setOrderNum(2);
						}else {
							buttonMenu.setOrderNum(3);
						}
						if(null != buttonMenu.getCoreMenuId() && buttonMenu.getCoreMenuId() != 0l) {
							coreMenuService.updateById(buttonMenu);
						}else {
							coreMenuService.save(buttonMenu);
						}
					}	

				}
			}
			//将所有权限菜单目录都付给super_admin
			List<CoreMenu> allMenuList = (List<CoreMenu>) redisTemplate.opsForList().leftPop(RedisKeyContant.CORE_MENU_ALL_MENUS);
			CoreRoleExample roleExample = new CoreRoleExample();
			roleExample.createCriteria().andRoleEqualTo("super_admin");
			List<CoreRole> roles = coreRoleService.queryCoreRolesByExample(roleExample);
			CoreRole superRole = null;
			if(null != roles && roles.size() > 0) {
				superRole = roles.get(0);
			}
			if(null != superRole) {
				//首先清空
				coreRoleMenuService.delCoreRoleMenuByRoleId(superRole.getCoreRoleId());
				for (CoreMenu coreMenu : allMenuList) {
					CoreRoleMenu coreRoleMenu = new CoreRoleMenu();
					coreRoleMenu.setCoreMenuId(coreMenu.getCoreMenuId());
					coreRoleMenu.setCoreRoleId(superRole.getCoreRoleId());
					coreRoleMenuService.insertCoreRoleMenu(coreRoleMenu);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "失败，程序发生异常！";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	}	
	
	@RequestMapping("/coreMenuTreeJson")
	@ResponseBody
	public Tree<CoreMenu> coreMenuTreeJson()throws Exception {
		Tree<CoreMenu> tree = coreMenuService.getCoreMenuTree();
		return tree;
	}
	
	@RequestMapping("/coreMenuTreeJsonByRoleId")
	@ResponseBody
	public Tree<CoreMenu> coreMenuTreeJsonByRoleId(String coreRoleId)throws Exception {
		Tree<CoreMenu> tree = null;
		if(StringUtils.isNotEmpty(coreRoleId)) {
			tree = coreMenuService.getCoreMenuTreeByRoleId(Long.parseLong(coreRoleId));
		}
		return tree;
	}
}
