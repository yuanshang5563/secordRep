package org.ys.core.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.ys.common.constant.CoreMenuType;
import org.ys.common.domain.Tree;
import org.ys.core.model.CoreMenu;
import org.ys.core.model.CoreMenuExample;
import org.ys.core.model.CoreUser;
import org.ys.core.model.CoreUserExample;
import org.ys.core.service.CoreMenuService;
import org.ys.core.service.CoreUserService;

@Controller
@RequestMapping("/manager/core/CoreManagerController")
public class CoreManagerController {
	@Autowired
	private CoreMenuService coreMenuService;
	
	@Autowired
	private CoreUserService coreUserService;	
	
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	
	@RequestMapping("/main")
	public String main(Model model) throws Exception {
		
		String username = "superadmin";
		if(StringUtils.isNotEmpty(username)) {
			CoreUserExample example = new CoreUserExample();
			example.createCriteria().andUserNameEqualTo(username.trim());
			List<CoreUser> users = coreUserService.queryCoreUsersByExample(example);
			CoreUser coreUser = null;
			if(null != users && users.size() > 0) {
				coreUser = users.get(0);
			}
			List<Tree<CoreMenu>> menus = coreMenuService.listMenuTreeByUserId(coreUser.getCoreUserId());
			model.addAttribute("menus", menus);
			model.addAttribute("name", coreUser.getRealName());
			model.addAttribute("picUrl","/img/photo_s.jpg");
			model.addAttribute("username", coreUser.getUserName());
		}
		
//		List<Tree<CoreMenu>> menus = coreMenuService.listMenuTreeByUserId(1l);
//		model.addAttribute("menus", menus);
//		model.addAttribute("name", "test");
//		model.addAttribute("picUrl","/img/photo_s.jpg");
//		model.addAttribute("username", "test111");
		return "/manager/main";
	}
	
	@RequestMapping("/index")
	public String index(Model model) throws Exception {
		return "/manager/index";
	}
	
//	@RequestMapping("/reLoadPermissions")
//	@ResponseBody
//	public Map<String,Object> reLoadPermissions() {
//		String msg = "";
//		boolean success = false;
//		try {
//			Map<HandlerMethod,String> methodAndUrlMap = new HashMap<HandlerMethod,String>();
//			Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
//			Set<RequestMappingInfo> mappingInfoSet = handlerMethodMap.keySet();
//			for (RequestMappingInfo requestMappingInfo : mappingInfoSet) {
//				PatternsRequestCondition patternsRequestCondition = requestMappingInfo.getPatternsCondition();
//				Set<String> patternsSet = patternsRequestCondition.getPatterns();
//				if(null != patternsSet && patternsSet.size() > 0) {
//					methodAndUrlMap.put(handlerMethodMap.get(requestMappingInfo), patternsSet.iterator().next());
//				}
//			}
//			Collection<HandlerMethod> handlerMethods = handlerMethodMap.values();
//			//一次找出所有菜单权限
//			Map<String,CoreMenu> existParentMenu = new HashMap<String,CoreMenu>();
//			CoreMenuExample example = new CoreMenuExample();
//			example.createCriteria().andMenuTypeEqualTo(CoreMenuType.MENU_TYPE_MENU);
//			List<CoreMenu> parentMenuList = coreMenuService.queryCoreMenusByExample(example);
//			if(null != parentMenuList && parentMenuList.size() > 0) {
//				for (CoreMenu parentMenu : parentMenuList) {
//					String menuUrl = parentMenu.getMenuUrl();
//					if(StringUtils.isNotEmpty(menuUrl) && menuUrl.contains("List")) {
//						int position = StringUtils.lastIndexOf(menuUrl, "/");
//						String menuActionUrl = StringUtils.substring(menuUrl, 0, position);
//						existParentMenu.put(menuActionUrl, parentMenu);
//					}
//				}
//			}
//			//找到所有权限
//			Map<String,CoreMenu> existPermissions = new HashMap<String,CoreMenu>();
//			example.clear();
//			example.createCriteria().andMenuTypeEqualTo(CoreMenuType.MENU_TYPE_PERMISSION);
//			List<CoreMenu> buttonList = coreMenuService.queryCoreMenusByExample(example);
//			if(null != buttonList && buttonList.size() > 0) {
//				for (CoreMenu button : buttonList) {
//					existPermissions.put(button.getMenuUrl(), button);
//				}
//			}
//			
//			for (HandlerMethod handlerMethod : handlerMethods) {
//				RequiresPermissions requiresPermissionsAnno = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
//				if(null == requiresPermissionsAnno) {
//					continue;
//				}
//				String[] permissionsArr = requiresPermissionsAnno.value();
//				String permission = null;
//				if(null != permissionsArr && permissionsArr.length > 0) {
//					permission = permissionsArr[0];
//				}
//				PermissionName permissionNameAnno = handlerMethod.getMethodAnnotation(PermissionName.class);
//				if(null == permissionNameAnno) {
//					continue;
//				}
//				String permissionNameVal = permissionNameAnno.value();
//				String permissionNameType = permissionNameAnno.type();
//				if(StringUtils.isNotEmpty(permission)&&StringUtils.isNotEmpty(permissionNameVal)
//				&&StringUtils.isNotEmpty(permissionNameType)) {
//					String menuUrl = methodAndUrlMap.get(handlerMethod);
//					int position = StringUtils.lastIndexOf(menuUrl, "/");
//					String menuActionUrl = StringUtils.substring(menuUrl, 0, position);
//					//先找父类
//					CoreMenu parentMenu = null;
//					if(StringUtils.equals(CoreMenuType.MENU_TYPE_PERMISSION, permissionNameType)){
//						if(existParentMenu.containsKey(menuActionUrl)) {
//							parentMenu = existParentMenu.get(menuActionUrl);
//						}
//						if(null != parentMenu) {
//							CoreMenu buttonMenu = null;
//							if(existPermissions.containsKey(menuUrl)) {
//								buttonMenu = existPermissions.get(menuUrl);
//							}else {
//								buttonMenu = new CoreMenu();
//								buttonMenu.setMenuUrl(menuUrl);
//								buttonMenu.setParentCoreMenuId(parentMenu.getCoreMenuId());
//							}
//							buttonMenu.setPermission(permission);
//							buttonMenu.setMenuType(CoreMenuType.MENU_TYPE_PERMISSION);
//							buttonMenu.setMenuName(permissionNameVal);
//							if(permission.contains("list")) {
//								buttonMenu.setOrderNum(0);
//							}else if(permission.contains("add")) {
//								buttonMenu.setOrderNum(1);
//							}else if(permission.contains("del")) {
//								buttonMenu.setOrderNum(2);
//							}else {
//								buttonMenu.setOrderNum(3);
//							}
//							if(null != buttonMenu.getCoreMenuId() && buttonMenu.getCoreMenuId() != 0l) {
//								coreMenuService.updateById(buttonMenu);
//							}else {
//								coreMenuService.save(buttonMenu);
//							}
//						}						
//					}
//
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			msg = "失败，程序发生异常！";
//		}
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("msg", msg);
//		map.put("success", success);
//		return map;
//	}
}
