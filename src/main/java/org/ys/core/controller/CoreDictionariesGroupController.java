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
import org.ys.common.domain.Tree;
import org.ys.common.page.PageBean;
import org.ys.common.utils.DateTimeConverter;
import org.ys.common.utils.RequsetUtils;
import org.ys.core.model.CoreDictionariesGroup;
import org.ys.core.model.CoreDictionariesGroupExample;
import org.ys.core.model.CoreDictionariesGroupExample.Criteria;
import org.ys.core.service.CoreDictionariesGroupService;
import org.ys.security.RequiredPermission;

@Controller
@RequestMapping("/manager/core/CoreDictionariesGroupController")
public class CoreDictionariesGroupController {
	@Autowired
	private CoreDictionariesGroupService coreDictionariesGroupService;
	
	@RequiredPermission(permissionName="列表页面",permission="ROLE_CORE_DICTIONARIESGROUP_LIST_PAGE")
	@RequestMapping("/coreDictionariesGroupList")
	public ModelAndView coreDictionariesGroupList() throws Exception {
		ModelAndView model = new ModelAndView("/manager/core_dictionaries_group/core_dictionaries_group_list");
		return model;
	}
	
	@RequiredPermission(permissionName="新增和修改页面",permission="ROLE_CORE_DICTIONARIESGROUP_ADD_EDIT_PAGE")
	@RequestMapping("/coreDictionariesGroupForm")
	public ModelAndView coreDictionariesGroupForm(Long coreDictGroupId,String actionType) throws Exception {
		CoreDictionariesGroup coreDictionariesGroup = null;
		String parentGroupName = "根节点";
		if(StringUtils.equals("edit", actionType.trim()) || StringUtils.equals("view", actionType.trim())) {
			coreDictionariesGroup = coreDictionariesGroupService.queryCoreDictionariesGroupById(coreDictGroupId);
		}else if(StringUtils.equals("addSub", actionType.trim())){
			coreDictionariesGroup = new CoreDictionariesGroup();
			coreDictionariesGroup.setParentCoreDictGroupId(coreDictGroupId);
		}else {
			coreDictionariesGroup = new CoreDictionariesGroup();
		}
		if(null != coreDictionariesGroup.getParentCoreDictGroupId() && coreDictionariesGroup.getParentCoreDictGroupId() != 0) {
			CoreDictionariesGroup parentDictionariesGroup = coreDictionariesGroupService.queryCoreDictionariesGroupById(coreDictionariesGroup.getParentCoreDictGroupId());
			if(null != parentDictionariesGroup) {
				parentGroupName = parentDictionariesGroup.getDictGroupName();
			}
		}
		ModelAndView model = new ModelAndView("/manager/core_dictionaries_group/core_dictionaries_group_form");
		model.addObject("parentGroupName", parentGroupName);
		model.addObject("coreDictionariesGroup", coreDictionariesGroup);
		model.addObject("actionType", actionType);
		return model;
	}
	
	@RequiredPermission(permissionName="新增和修改",permission="ROLE_CORE_DICTIONARIESGROUP_ADD_EDIT")
	@RequestMapping("/saveCoreDictionariesGroupForm")
	@ResponseBody
	public Map<String,Object> saveCoreDictionariesGroupForm(HttpServletRequest request)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			Map<String,Object> paramMap = RequsetUtils.getParamsMap(request);
			String coreDictGroupId = request.getParameter("coreDictGroupId");
			CoreDictionariesGroup coreDictionariesGroup = null;
			if(StringUtils.isNotEmpty(coreDictGroupId)) {
				coreDictionariesGroup = coreDictionariesGroupService.queryCoreDictionariesGroupById(Long.parseLong(coreDictGroupId));
			}else {
				coreDictionariesGroup = new CoreDictionariesGroup();
			}
			BeanUtilsBean.getInstance().getConvertUtils().register(new DateTimeConverter(), Date.class);
			BeanUtils.populate(coreDictionariesGroup, paramMap);
			
			boolean existFlag = false;
			if(null == coreDictionariesGroup.getCoreDictGroupId() || coreDictionariesGroup.getCoreDictGroupId() == 0l) {
				String dictGroupCode = coreDictionariesGroup.getDictGroupCode();
				if(StringUtils.isNotEmpty(dictGroupCode)) {
					CoreDictionariesGroupExample example = new CoreDictionariesGroupExample();
					example.createCriteria().andDictGroupCodeEqualTo(dictGroupCode.trim());
					List<CoreDictionariesGroup> groupList = coreDictionariesGroupService.queryCoreDictionariesGroupsByExample(example);
					if(null != groupList && groupList.size() > 0) {
						existFlag = true;
					}
				}				
			}
			if(!existFlag) {
				if(null == coreDictionariesGroup.getCoreDictGroupId() || coreDictionariesGroup.getCoreDictGroupId() == 0l) {
					coreDictionariesGroupService.save(coreDictionariesGroup);
				}else {
					coreDictionariesGroupService.updateById(coreDictionariesGroup);
				}
				msg = "操作字典组成功！";
				success = true;
			}else {
				msg = "操作字典组失败，已存在相同代码的字典组！";
				success = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,操作字典组失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	}  
	
	@RequiredPermission(permissionName="删除",permission="ROLE_CORE_DICTIONARIESGROUP_DEL")
	@RequestMapping("/deleteCoreDictionariesGroup")
	@ResponseBody
	public Map<String,Object> deleteCoreDictionariesGroup(Long coreDictGroupId)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			coreDictionariesGroupService.delCoreDictionariesGroupById(coreDictGroupId);
			msg = "删除字典组成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,删除字典组失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	} 
	
	@RequestMapping("/ajaxFindCoreDictionariesGroupById")
	@ResponseBody
	public Map<String,Object> ajaxFindCoreDictionariesGroupById(Long coreDictGroupId)throws Exception {
		String msg = "";
		boolean success = false;
		CoreDictionariesGroup coreDictionariesGroup = null;
		try {
			coreDictionariesGroup = coreDictionariesGroupService.queryCoreDictionariesGroupById(coreDictGroupId);
			msg = "查找字典组成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,查找字典组失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(success){
			map.put("data", coreDictionariesGroup);
		}else{
			map.put("errorMessage", msg);
		}
		map.put("success", success);
		return map;
	} 
	
	@RequiredPermission(permissionName="列表数据1",permission="ROLE_CORE_DICTIONARIESGROUP_LIST1")
	@RequestMapping("/coreDictionariesGroupListJsonData")
	@ResponseBody
	public Map<String,Object> getCoreDictionariesGroupListJsonData(HttpServletRequest request)throws Exception {
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String dictGroupName = request.getParameter("dictGroupName");
		String dictGroupCode = request.getParameter("dictGroupCode");
		CoreDictionariesGroupExample example = new CoreDictionariesGroupExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(dictGroupName)){
			criteria.andDictGroupNameLike(dictGroupName.trim()+"%");
		}
		if(StringUtils.isNotEmpty(dictGroupCode)){
			criteria.andDictGroupCodeLike(dictGroupCode.trim()+"%");
		}
		if(StringUtils.isEmpty(start) || StringUtils.equals(start, "0")) {
			start = "1";
		}
		if(StringUtils.isEmpty(limit)) {
			limit = "10";
		}
		List<CoreDictionariesGroup> groups = null;
		long count = 0;
		PageBean<CoreDictionariesGroup> pageBean = coreDictionariesGroupService.pageCoreDictionariesGroupsByExample(example, Integer.parseInt(start.trim()), Integer.parseInt(limit.trim()));
		if(null != pageBean) {
			groups = pageBean.getList();
			count = pageBean.getTotal();
		}else {
			groups = new ArrayList<CoreDictionariesGroup>();
		}
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("count", count);
		maps.put("root", groups);
		return maps;
	}	
	
	@RequiredPermission(permissionName="列表数据2",permission="ROLE_CORE_DICTIONARIESGROUP_LIST2")
	@RequestMapping("/coreDictionariesGroupListJsonDataNoPage")
	@ResponseBody
	public List<CoreDictionariesGroup> coreDictionariesGroupListJsonDataNoPage(HttpServletRequest request)throws Exception {
		CoreDictionariesGroupExample example = new CoreDictionariesGroupExample();
		Criteria criteria = example.createCriteria();
		criteria.andCoreDictGroupIdNotEqualTo(0l);
		String dictGroupName = request.getParameter("dictGroupName");
		if(StringUtils.isNotEmpty(dictGroupName)) {
			criteria.andDictGroupNameLike("%"+dictGroupName+"%");
		}
		List<CoreDictionariesGroup> groups = coreDictionariesGroupService.queryCoreDictionariesGroupsByExample(example);
		if(null == groups) {
			groups = new ArrayList<CoreDictionariesGroup>();
		}
		return groups;
	}	
	
	@RequestMapping("/coreDictionariesGroupTreeJson")
	@ResponseBody
	public Tree<CoreDictionariesGroup> coreDictionariesGroupTreeJson()throws Exception {
		CoreDictionariesGroupExample example = new CoreDictionariesGroupExample();
		Tree<CoreDictionariesGroup> tree = coreDictionariesGroupService.getCoreDictionariesGroupTree(example);
		return tree;
	}
	
	@RequestMapping("/coreDictionariesGroupTree")
	public String coreDictionariesGroupTree()throws Exception {
		return "/manager/core_dictionaries_group/core_dictionaries_group_tree";
	}
}
