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
import org.ys.core.model.CoreDept;
import org.ys.core.model.CoreDeptExample;
import org.ys.core.model.CoreDeptExample.Criteria;
import org.ys.core.service.CoreDeptService;
import org.ys.security.RequiredPermission;

@Controller
@RequestMapping("/manager/core/CoreDeptController")
public class CoreDeptController {
	@Autowired
	private CoreDeptService coreDeptService;
	
	@RequiredPermission(permissionName="列表页面",permission="ROLE_CORE_DEPT_LIST_PAGE")
	@RequestMapping("/coreDeptList")
	public ModelAndView coreDeptList() throws Exception {
		ModelAndView model = new ModelAndView("/manager/core_dept/core_dept_list");
		return model;
	}
	
	@RequiredPermission(permissionName="新增和修改页面",permission="ROLE_CORE_DEPT_ADD_EDIT_PAGE")
	@RequestMapping("/coreDeptForm")
	public ModelAndView coreDeptForm(Long coreDeptId,String actionType) throws Exception {
		CoreDept coreDept = null;
		String parentDeptName = "根节点";
		if(StringUtils.equals("edit", actionType.trim()) || StringUtils.equals("view", actionType.trim())) {
			coreDept = coreDeptService.queryCoreDeptById(coreDeptId);
		}else if(StringUtils.equals("addSub", actionType.trim())){
			coreDept = new CoreDept();
			coreDept.setParentCoreDeptId(coreDeptId);
		}else {
			coreDept = new CoreDept();
		}
		if(null != coreDept.getParentCoreDeptId() && coreDept.getParentCoreDeptId() != 0) {
			CoreDept parentDept = coreDeptService.queryCoreDeptById(coreDept.getParentCoreDeptId());
			if(null != parentDept) {
				parentDeptName = parentDept.getDeptName();
			}
		}
		ModelAndView model = new ModelAndView("/manager/core_dept/core_dept_form");
		model.addObject("parentDeptName", parentDeptName);
		model.addObject("coreDept", coreDept);
		model.addObject("actionType", actionType);
		return model;
	}
	
	@RequiredPermission(permissionName="新增和修改",permission="ROLE_CORE_DEPT_ADD_EDIT")
	@RequestMapping("/saveCoreDeptForm")
	@ResponseBody
	public Map<String,Object> saveCoreDeptForm(HttpServletRequest request)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			Map<String,Object> paramMap = RequsetUtils.getParamsMap(request);
			String coreDeptId = request.getParameter("coreDeptId");
			CoreDept coreDept = null;
			if(StringUtils.isNotEmpty(coreDeptId)) {
				coreDept = coreDeptService.queryCoreDeptById(Long.parseLong(coreDeptId));
			}else {
				coreDept = new CoreDept();
			}
			BeanUtilsBean.getInstance().getConvertUtils().register(new DateTimeConverter(), Date.class);
			BeanUtils.populate(coreDept, paramMap);
			if(null == coreDept.getCoreDeptId() || coreDept.getCoreDeptId() == 0) {
				coreDeptService.save(coreDept);
			}else {
				coreDeptService.updateById(coreDept);
			}
			msg = "操作部门成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,操作部门失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	}  
	
	/**
	 * 删除方法
	 * @param coreDeptId
	 * @return
	 * @throws Exception
	 */
	@RequiredPermission(permissionName="删除",permission="ROLE_CORE_DEPT_DEL")
	@RequestMapping("/deleteCoreDept")
	@ResponseBody
	public Map<String,Object> deleteCoreDept(Long coreDeptId)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			coreDeptService.delCoreDeptById(coreDeptId);
			msg = "删除部门成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,删除部门失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	} 
	
	@RequestMapping("/ajaxFindCoreDeptById")
	@ResponseBody
	public Map<String,Object> ajaxFindCoreDeptById(Long coreDeptId)throws Exception {
		String msg = "";
		boolean success = false;
		CoreDept coreDept = null;
		try {
			coreDept = coreDeptService.queryCoreDeptById(coreDeptId);
			msg = "查找部门成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,查找部门失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(success){
			map.put("data", coreDept);
		}else{
			map.put("errorMessage", msg);
		}
		map.put("success", success);
		return map;
	} 
	
	@RequiredPermission(permissionName="列表数据1",permission="ROLE_CORE_DEPT_LIST1")
	@RequestMapping("/coreDeptListJsonData")
	@ResponseBody
	public Map<String,Object> getCoreDeptListJsonData(HttpServletRequest request)throws Exception {
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String deptName = request.getParameter("deptName");
		String deptCode = request.getParameter("deptCode");
		CoreDeptExample example = new CoreDeptExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(deptName)){
			criteria.andDeptNameLike(deptName.trim()+"%");
		}
		if(StringUtils.isNotEmpty(deptCode)){
			criteria.andDeptCodeLike(deptCode.trim()+"%");
		}
		if(StringUtils.isEmpty(start) || StringUtils.equals(start, "0")) {
			start = "1";
		}
		if(StringUtils.isEmpty(limit)) {
			limit = "10";
		}
		List<CoreDept> depts = null;
		long count = 0;
		PageBean<CoreDept> pageBean = coreDeptService.pageCoreDeptsByExample(example, Integer.parseInt(start.trim()), Integer.parseInt(limit.trim()));
		if(null != pageBean) {
			depts = pageBean.getList();
			count = pageBean.getTotal();
		}else {
			depts = new ArrayList<CoreDept>();
		}
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("count", count);
		maps.put("root", depts);
		return maps;
	}	
	
	@RequiredPermission(permissionName="列表数据2",permission="ROLE_CORE_DEPT_LIST2")
	@RequestMapping("/coreDeptListJsonDataNoPage")
	@ResponseBody
	public List<CoreDept> coreDeptListJsonDataNoPage(HttpServletRequest request)throws Exception {
		CoreDeptExample example = new CoreDeptExample();
		Criteria criteria = example.createCriteria();
		criteria.andCoreDeptIdNotEqualTo(0l);
		String deptName = request.getParameter("deptName");
		if(StringUtils.isNotEmpty(deptName)) {
			criteria.andDeptNameLike("%"+deptName+"%");
		}
		List<CoreDept> deptList = coreDeptService.queryCoreDeptsByExample(example);
		if(null == deptList) {
			deptList = new ArrayList<CoreDept>();
		}
		return deptList;
	}	
	
	@RequestMapping("/coreDeptTreeJson")
	@ResponseBody
	public Tree<CoreDept> coreDeptTreeJson()throws Exception {
		CoreDeptExample example = new CoreDeptExample();
		Tree<CoreDept> tree = coreDeptService.getCoreDeptTree(example);
		return tree;
	}
	
	@RequestMapping("/coreDeptTree")
	public String coreDeptTree()throws Exception {
		return "/manager/core_dept/core_dept_tree";
	}
}
