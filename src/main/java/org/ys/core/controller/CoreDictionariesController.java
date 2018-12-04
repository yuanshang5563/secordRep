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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.ys.common.page.PageBean;
import org.ys.common.utils.DateTimeConverter;
import org.ys.common.utils.RequsetUtils;
import org.ys.core.model.CoreDictionaries;
import org.ys.core.model.CoreDictionariesExample;
import org.ys.core.model.CoreDictionariesExample.Criteria;
import org.ys.core.model.CoreDictionariesGroup;
import org.ys.core.service.CoreDictionariesGroupService;
import org.ys.core.service.CoreDictionariesService;
import org.ys.security.RequiredPermission;

@Controller
@RequestMapping("/manager/core/CoreDictionariesController")
public class CoreDictionariesController {
	
	@Autowired
	private CoreDictionariesService coreDictionariesService;
	
	@Autowired
	private CoreDictionariesGroupService coreDictionariesGroupService;
	
	@RequiredPermission(permissionName="列表页面",permission="ROLE_CORE_DICTIONARIES_LIST_PAGE")
	@RequestMapping("/coreDictionariesList")
	public ModelAndView coreDictionariesList() throws Exception {
		ModelAndView model = new ModelAndView("/manager/core_dictionaries/core_dictionaries_list");
		return model;
	}
	
	@RequiredPermission(permissionName="新增和修改页面",permission="ROLE_CORE_DICTIONARIES_ADD_EDIT_PAGE")
	@RequestMapping("/coreDictionariesForm")
	public ModelAndView coreDictionariesForm(Long coreDictId,String actionType) throws Exception {
		CoreDictionaries coreDictionaries = null;
		if(StringUtils.equals("add", actionType.trim())) {
			coreDictionaries = new CoreDictionaries();
		}else {
			coreDictionaries = coreDictionariesService.queryCoreDictionariesById(coreDictId);
		}
		String dictGroupName = null;
		if(null != coreDictionaries.getCoreDictGroupId() && coreDictionaries.getCoreDictGroupId() != 0) {
			CoreDictionariesGroup coreDictionariesGroup = coreDictionariesGroupService.queryCoreDictionariesGroupById(coreDictionaries.getCoreDictGroupId());
			if(null != coreDictionariesGroup) {
				dictGroupName = coreDictionariesGroup.getDictGroupName();
			}
		}
		ModelAndView model = new ModelAndView("/manager/core_dictionaries/core_dictionaries_form");
		model.addObject("coreDictionaries", coreDictionaries);
		model.addObject("actionType", actionType);
		model.addObject("dictGroupName", dictGroupName);
		return model;
	}
	
	@RequiredPermission(permissionName="新增和修改",permission="ROLE_CORE_DICTIONARIES_ADD_EDIT")
	@RequestMapping("/saveCoreDictionariesForm")
	@ResponseBody
	public Map<String,Object> saveCoreDictionariesForm(HttpServletRequest request)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			Map<String,Object> paramMap = RequsetUtils.getParamsMap(request);
			String coreDictId = request.getParameter("coreDictId");
			CoreDictionaries coreDictionaries = null;
			if(StringUtils.isNotEmpty(coreDictId)) {
				coreDictionaries = coreDictionariesService.queryCoreDictionariesById(Long.parseLong(coreDictId));
			}else {
				coreDictionaries = new CoreDictionaries();
			}
			BeanUtilsBean.getInstance().getConvertUtils().register(new DateTimeConverter(), Date.class);
			BeanUtils.populate(coreDictionaries, paramMap);
			if(null != coreDictionaries.getCoreDictId() && coreDictionaries.getCoreDictId() != 0l) {
				coreDictionariesService.updateById(coreDictionaries);
			}else {
				coreDictionariesService.save(coreDictionaries);
			}
			msg = "操作字典成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,操作字典失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	}  
	
	@RequiredPermission(permissionName="删除",permission="ROLE_CORE_DICTIONARIES_DEL")
	@RequestMapping("/deleteCoreDictionaries")
	@ResponseBody
	public Map<String,Object> deleteCoreDictionaries(Long coreDictId)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			coreDictionariesService.delCoreDictionariesById(coreDictId);
			msg = "删除字典成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,删除字典失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	} 
	
	@RequestMapping("/ajaxFindCoreDictionariesById")
	@ResponseBody
	public Map<String,Object> ajaxFindCoreDictionariesById(Long coreDictId)throws Exception {
		String msg = "";
		boolean success = false;
		CoreDictionaries coreDictionaries = null;
		try {
			coreDictionaries = coreDictionariesService.queryCoreDictionariesById(coreDictId);
			msg = "查找字典成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,查找字典失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(success){
			map.put("data", coreDictionaries);
		}else{
			map.put("errorMessage", msg);
		}
		map.put("success", success);
		return map;
	} 
	
	@RequiredPermission(permissionName="列表数据",permission="ROLE_CORE_DICTIONARIES_LIST")
	@RequestMapping("/coreDictionariesListJsonData")
	@ResponseBody
	public Map<String,Object> coreDictionariesListJsonData(HttpServletRequest request)throws Exception {
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String dictValue = request.getParameter("dictValue");
		CoreDictionariesExample example = new CoreDictionariesExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(dictValue)){
			criteria.andDictValueLike(dictValue.trim()+"%");
		}
		String coreDictGroupId = request.getParameter("coreDictGroupId");
		if(StringUtils.isNotEmpty(coreDictGroupId)) {
			Set<CoreDictionariesGroup> groups = coreDictionariesGroupService.queryAllSubCoreDictionariesGroupsByDictGroupId(Long.parseLong(coreDictGroupId.trim()));
			if(null != groups && groups.size() > 0) {
				List<Long> groupIds = new ArrayList<Long>();
				for (CoreDictionariesGroup group : groups) {
					groupIds.add(group.getCoreDictGroupId());
				}
				criteria.andCoreDictGroupIdIn(groupIds);
			}
		}
		if(StringUtils.isEmpty(start) || StringUtils.equals(start, "0")) {
			start = "1";
		}
		if(StringUtils.isEmpty(limit)) {
			limit = "10";
		}
		List<CoreDictionaries> coreDictionariesList = null;
		long total = 0;
		PageBean<CoreDictionaries> pageBean = coreDictionariesService.pageCoreDictionariesByExample(example, Integer.parseInt(start.trim()), Integer.parseInt(limit.trim()));
		if(null != pageBean) {
			coreDictionariesList = pageBean.getList();
			total = pageBean.getTotal();
		}else {
			coreDictionariesList = new ArrayList<CoreDictionaries>();
		}
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("total", total);
		maps.put("rows", coreDictionariesList);
		return maps;
	}	
}
