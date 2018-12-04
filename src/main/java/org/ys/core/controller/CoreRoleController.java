package org.ys.core.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.ys.common.page.PageBean;
import org.ys.common.utils.DateTimeConverter;
import org.ys.common.utils.RequsetUtils;
import org.ys.core.model.CoreRole;
import org.ys.core.model.CoreRoleExample;
import org.ys.core.model.CoreRoleExample.Criteria;
import org.ys.core.service.CoreRoleService;

@Controller
@RequestMapping("/manager/core/CoreRoleController")
public class CoreRoleController {
	
	@Autowired
	private CoreRoleService coreRoleService;
	
	@RequestMapping("/coreRoleList")
	public ModelAndView coreRoleList() throws Exception {
		ModelAndView model = new ModelAndView("/manager/core_role/core_role_list");
		return model;
	}
	
	@RequestMapping("/coreRoleForm")
	public ModelAndView coreRoleForm(Long coreRoleId,String actionType) throws Exception {
		CoreRole coreRole = null;
		if(StringUtils.equals("add", actionType.trim())) {
			coreRole = new CoreRole();
		}else {
			coreRole = coreRoleService.queryCoreRoleById(coreRoleId);
		}
		ModelAndView model = new ModelAndView("/manager/core_role/core_role_form");
		model.addObject("actionType", actionType);
		model.addObject("coreRole", coreRole);
		model.addObject("actionType", actionType);
		return model;
	}
	
	@RequestMapping("/saveCoreRoleForm")
	@ResponseBody
	public Map<String,Object> saveCoreRoleForm(HttpServletRequest request)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			Map<String,Object> paramMap = RequsetUtils.getParamsMap(request);
			String coreRoleId = request.getParameter("coreRoleId");
			CoreRole coreRole = null;
			if(StringUtils.isNotEmpty(coreRoleId)) {
				coreRole = coreRoleService.queryCoreRoleById(Long.parseLong(coreRoleId));
			}else {
				coreRole = new CoreRole();
			}
			BeanUtilsBean.getInstance().getConvertUtils().register(new DateTimeConverter(), Date.class);
			BeanUtils.populate(coreRole, paramMap);
			if(null == coreRole.getCreatedTime()) {
				coreRole.setCreatedTime(new Date());
			}
			if(null == coreRole.getModifiedTime()) {
				coreRole.setModifiedTime(new Date());
			}
			String coreMenuIds = request.getParameter("coreMenuIds");
			String[] coreMenuIdArr = null;
			if(StringUtils.isNoneEmpty(coreMenuIds)) {
				coreMenuIdArr = coreMenuIds.trim().split(",");
			}
			
			boolean existFlag = false;
			if(null == coreRole.getCoreRoleId() || coreRole.getCoreRoleId() == 0) {
				String role = coreRole.getRole();
				if(StringUtils.isNotEmpty(role)) {
					CoreRoleExample example = new CoreRoleExample();
					example.createCriteria().andRoleEqualTo(role);
					List<CoreRole> coreRoleList = coreRoleService.queryCoreRolesByExample(example);
					if(null != coreRoleList && coreRoleList.size() > 0) {
						existFlag = true;
					}
				}				
			}
			if(!existFlag) {
				coreRoleService.saveOrUpdateCoreRoleAndCoreMenu(coreRole, coreMenuIdArr);
				msg = "操作角色成功！";
				success = true;
			}else {
				msg = "操作角色失败，已存在相同名字的角色！";
				success = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,操作角色失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	}  
	
	@RequestMapping("/deleteCoreRole")
	@ResponseBody
	public Map<String,Object> deleteCoreRole(Long coreRoleId)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			coreRoleService.delCoreRoleById(coreRoleId);
			msg = "删除角色成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,删除角色失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	} 
	
	@RequestMapping("/ajaxFindCoreRoleById")
	@ResponseBody
	public Map<String,Object> ajaxFindCoreRoleById(Long coreRoleId)throws Exception {
		String msg = "";
		boolean success = false;
		CoreRole coreRole = null;
		try {
			coreRole = coreRoleService.queryCoreRoleById(coreRoleId);
			msg = "查找角色成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,查找角色失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(success){
			map.put("data", coreRole);
		}else{
			map.put("errorMessage", msg);
		}
		map.put("success", success);
		return map;
	} 
	
	@RequestMapping("/coreRoleListJsonData")
	@ResponseBody
	public Map<String,Object> coreRoleListJsonData(HttpServletRequest request)throws Exception {
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String roleName = request.getParameter("roleName");
		CoreRoleExample example = new CoreRoleExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(roleName)){
			criteria.andRoleNameLike("%"+roleName.trim()+"%");
		}
		if(StringUtils.isEmpty(start) || StringUtils.equals(start, "0")) {
			start = "1";
		}
		if(StringUtils.isEmpty(limit)) {
			limit = "10";
		}
		List<CoreRole> coreRoles = null;
		long total = 0;
		PageBean<CoreRole> pageBean = coreRoleService.pageCoreRolesByExample(example, Integer.parseInt(start.trim()), Integer.parseInt(limit.trim()));
		if(null != pageBean) {
			coreRoles = pageBean.getList();
			total = pageBean.getTotal();
		}else {
			coreRoles = new ArrayList<CoreRole>();
		}
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("total", total);
		maps.put("rows", coreRoles);
		return maps;
	}	
}
