package org.ys.core.controller;

import java.util.ArrayList;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.ys.common.page.PageBean;
import org.ys.common.utils.DateTimeConverter;
import org.ys.common.utils.RequsetUtils;
import org.ys.core.model.*;
import org.ys.core.model.CoreUserExample.Criteria;
import org.ys.core.service.CoreDeptService;
import org.ys.core.service.CoreDictionariesService;
import org.ys.core.service.CoreRoleService;
import org.ys.core.service.CoreUserService;
import org.ys.security.RequiredPermission;

@Controller
@RequestMapping("/manager/core/CoreUserController")
public class CoreUserController {
	@Autowired
	private CoreUserService coreUserService;
	
	@Autowired
	private CoreRoleService coreRoleService;
	
	@Autowired
	private CoreDeptService coreDeptService;

	@Autowired
	private CoreDictionariesService coreDictionariesService;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@RequiredPermission(permissionName="列表页面",permission="ROLE_CORE_USER_LIST_PAGE")
	@RequestMapping("/coreUserList")
	public ModelAndView coreUserList() throws Exception {
		ModelAndView model = new ModelAndView("/manager/core_user/core_user_list");
		return model;
	}
	
	@RequiredPermission(permissionName="新增和修改页面",permission="ROLE_CORE_USER_ADD_EDIT_PAGE")
	@RequestMapping("/coreUserForm")
	public ModelAndView coreUserForm(Long coreUserId,String actionType) throws Exception {
		CoreUser coreUser = null;
		if(StringUtils.equals("add", actionType.trim())) {
			coreUser = new CoreUser();
		}else {
			coreUser = coreUserService.queryCoreUserById(coreUserId);
		}
		String deptName = null;
		if(null != coreUser.getCoreDeptId() && coreUser.getCoreDeptId() != 0) {
			CoreDept coreDept = coreDeptService.queryCoreDeptById(coreUser.getCoreDeptId());
			if(null != coreDept) {
				deptName = coreDept.getDeptName();
			}
		}
		List<CoreRole> coreRoles = coreRoleService.queryCoreRolesByExample(new CoreRoleExample());
		List<CoreRole> existCoreRoles = null;
		if(null != coreUserId && coreUserId != 0l) {
			existCoreRoles = coreRoleService.listCoreRolesByUserId(coreUserId);
			//为前台checkbox选中
			if(null != existCoreRoles && existCoreRoles.size() > 0) {
				for (CoreRole coreRole : coreRoles) {
					if(!existCoreRoles.contains(coreRole)) {
						coreRole.setCheckFlag(true);
					}
				}
			}
		}
		ModelAndView model = new ModelAndView("/manager/core_user/core_user_form");
		Map<String, List<CoreDictionaries>> dictMap = coreUserService.initDictionaries();
		if(null != dictMap && dictMap.size() > 0){
			Set<String> dictMapKeys = dictMap.keySet();
			for (String dictMapKey : dictMapKeys) {
				model.addObject(dictMapKey,dictMap.get(dictMapKey));
			}
		}
		model.addObject("coreUser", coreUser);
		model.addObject("actionType", actionType);
		model.addObject("coreRoles", coreRoles);
		model.addObject("deptName", deptName);
		return model;
	}
	
	@RequiredPermission(permissionName="新增和修改",permission="ROLE_CORE_USER_ADD_EDIT")
	@RequestMapping("/saveCoreUserForm")
	@ResponseBody
	public Map<String,Object> saveCoreUserForm(HttpServletRequest request)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			Map<String,Object> paramMap = RequsetUtils.getParamsMap(request);
			String coreUserId = request.getParameter("coreUserId");
			CoreUser coreUser = null;
			if(StringUtils.isNotEmpty(coreUserId)) {
				coreUser = coreUserService.queryCoreUserById(Long.parseLong(coreUserId));
			}else {
				coreUser = new CoreUser();
			}
			BeanUtilsBean.getInstance().getConvertUtils().register(new DateTimeConverter(), Date.class);
			BeanUtils.populate(coreUser, paramMap);
			if(null == coreUser.getCreatedTime()) {
				coreUser.setCreatedTime(new Date());
			}
			if(null == coreUser.getModifiedTime()) {
				coreUser.setModifiedTime(new Date());
			}
			String coreRoleIds = request.getParameter("coreRoleIds");
			String[] coreRoleArr = null;;
			if(StringUtils.isNotEmpty(coreRoleIds)) {
				coreRoleArr = coreRoleIds.trim().split(",");
			}
			String actionType = request.getParameter("actionType");
			if(StringUtils.equals(actionType, "add") && StringUtils.isNotEmpty(coreUser.getPassword())) {
				coreUser.setPassword(bCryptPasswordEncoder.encode(coreUser.getPassword().trim()));
			}
			coreUserService.saveOrUpdateCoreUserAndRoles(coreUser, coreRoleArr);
			msg = "操作用户成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,操作用户失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	}  
	
	@RequiredPermission(permissionName="删除",permission="ROLE_CORE_USER_DEL")
	@RequestMapping("/deleteCoreUser")
	@ResponseBody
	public Map<String,Object> deleteCoreUser(Long coreUserId)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			coreUserService.delCoreUserById(coreUserId);
			msg = "删除用户成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,删除用户失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	} 
	
	@RequestMapping("/ajaxFindCoreUserById")
	@ResponseBody
	public Map<String,Object> ajaxFindCoreUserById(Long coreUserId)throws Exception {
		String msg = "";
		boolean success = false;
		CoreUser coreUser = null;
		try {
			coreUser = coreUserService.queryCoreUserById(coreUserId);
			msg = "查找用户成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,查找用户失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(success){
			map.put("data", coreUser);
		}else{
			map.put("errorMessage", msg);
		}
		map.put("success", success);
		return map;
	} 
	
	@RequiredPermission(permissionName="列表数据",permission="ROLE_CORE_USER_LIST")
	@RequestMapping("/coreUserListJsonData")
	@ResponseBody
	public Map<String,Object> coreUserListJsonData(HttpServletRequest request)throws Exception {
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String userName = request.getParameter("userName");
		CoreUserExample example = new CoreUserExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(userName)){
			criteria.andUserNameLike(userName.trim()+"%");
		}
		String coreDeptId = request.getParameter("coreDeptId");
		if(StringUtils.isNotEmpty(coreDeptId)) {
			Set<CoreDept> depts = coreDeptService.queryAllSubCoreDeptsByDeptId(Long.parseLong(coreDeptId.trim()));
			if(null != depts && depts.size() > 0) {
				List<Long> deptIds = new ArrayList<Long>();
				for (CoreDept dept : depts) {
					deptIds.add(dept.getCoreDeptId());
				}
				criteria.andCoreDeptIdIn(deptIds);
			}
		}
		if(StringUtils.isEmpty(start) || StringUtils.equals(start, "0")) {
			start = "1";
		}
		if(StringUtils.isEmpty(limit)) {
			limit = "10";
		}
		List<CoreUser> coreUsers = null;
		long total = 0;
		PageBean<CoreUser> pageBean = coreUserService.pageCoreUsersByExample(example, Integer.parseInt(start.trim()), Integer.parseInt(limit.trim()));
		if(null != pageBean) {
			coreUsers = pageBean.getList();
			total = pageBean.getTotal();
		}else {
			coreUsers = new ArrayList<CoreUser>();
		}
		//转换字典码
		coreUserService.convertCoreUsersDictionaries(coreUsers);
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("total", total);
		maps.put("rows", coreUsers);
		return maps;
	}	
	
	@RequiredPermission(permissionName="密码重设页面",permission="ROLE_CORE_USER_RESET_PAGE")
	@RequestMapping("/resetPwd")
	public ModelAndView resetPwd(String coreUserId) throws Exception {
		ModelAndView model = new ModelAndView("/manager/core_user/core_user_reset_pwd");
		model.addObject("coreUserId", coreUserId);
		return model;
	}
	
	@RequiredPermission(permissionName="密码重设",permission="ROLE_CORE_USER_RESET")
	@RequestMapping("/saveResetPwd")
	@ResponseBody
	public Map<String,Object> saveResetPwd(HttpServletRequest request)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			String coreUserId = request.getParameter("coreUserId");
			String password = request.getParameter("password");
			CoreUser coreUser = null;
			if(StringUtils.isNotEmpty(coreUserId)) {
				coreUser = coreUserService.queryCoreUserById(Long.parseLong(coreUserId));
			}
			if(null != coreUser) {
				coreUser.setPassword(bCryptPasswordEncoder.encode(password));
				coreUser.setModifiedTime(new Date());
				coreUserService.updateById(coreUser);
				msg = "重置成功！";
				success = true;
			}else {
				msg = "重置失败，未找到用户！ ";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,重置失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	} 
}
