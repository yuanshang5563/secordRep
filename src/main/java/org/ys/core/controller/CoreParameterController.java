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
import org.ys.common.constant.CoreMenuType;
import org.ys.common.page.PageBean;
import org.ys.common.utils.DateTimeConverter;
import org.ys.common.utils.RequsetUtils;
import org.ys.core.model.CoreParameter;
import org.ys.core.model.CoreParameterExample;
import org.ys.core.model.CoreParameterExample.Criteria;
import org.ys.core.service.CoreParameterService;

@Controller
@RequestMapping("/manager/core/CoreParameterController")
public class CoreParameterController {
	
	@Autowired
	private CoreParameterService coreParameterService;
	
	@RequestMapping("/coreParameterList")
	public ModelAndView coreParameterList() throws Exception {
		ModelAndView model = new ModelAndView("/manager/core_parameter/core_parameter_list");
		return model;
	}
	
	@RequestMapping("/coreParameterForm")
	public ModelAndView coreParameterForm(Long coreParamId,String actionType) throws Exception {
		CoreParameter coreParameter = null;
		if(StringUtils.equals("add", actionType.trim())) {
			coreParameter = new CoreParameter();
		}else {
			coreParameter = coreParameterService.queryCoreParameterById(coreParamId);
		}
		ModelAndView model = new ModelAndView("/manager/core_parameter/core_parameter_form");
		model.addObject("actionType", actionType);
		model.addObject("coreParameter", coreParameter);
		return model;
	}
	
	@RequestMapping("/saveCoreParameterForm")
	@ResponseBody
	public Map<String,Object> saveCoreParameterForm(HttpServletRequest request)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			Map<String,Object> paramMap = RequsetUtils.getParamsMap(request);
			String coreParamId = request.getParameter("coreParamId");
			CoreParameter coreParameter = null;
			if(StringUtils.isNotEmpty(coreParamId)) {
				coreParameter = coreParameterService.queryCoreParameterById(Long.parseLong(coreParamId));
			}else {
				coreParameter = new CoreParameter();
			}
			BeanUtilsBean.getInstance().getConvertUtils().register(new DateTimeConverter(), Date.class);
			BeanUtils.populate(coreParameter, paramMap);
			if(null == coreParameter.getCreatedTime()) {
				coreParameter.setCreatedTime(new Date());
			}
			if(null == coreParameter.getModifiedTime()) {
				coreParameter.setModifiedTime(new Date());
			}
			
			boolean existFlag = false;
			if(null == coreParameter.getCoreParamId() || coreParameter.getCoreParamId() == 0l) {
				String paramCode = coreParameter.getParamCode();
				if(StringUtils.isNotEmpty(paramCode)) {
					CoreParameterExample example = new CoreParameterExample();
					example.createCriteria().andParamCodeEqualTo(paramCode);
					List<CoreParameter> coreParameterList = coreParameterService.queryCoreParametersByExample(example);
					if(null != coreParameterList && coreParameterList.size() > 0) {
						existFlag = true;
					}
				}				
			}
			if(!existFlag) {
				if(null != coreParameter.getCoreParamId() && coreParameter.getCoreParamId() != 0l) {
					coreParameterService.updateById(coreParameter);
				}else {
					coreParameterService.save(coreParameter);
				}
				msg = "操作参数成功！";
				success = true;
			}else {
				msg = "操作参数失败，已存在相同代码的参数！";
				success = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,操作参数失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	}  
	
	@RequestMapping("/deleteCoreParameter")
	@ResponseBody
	public Map<String,Object> deleteCoreParameter(Long coreParamId)throws Exception {
		String msg = "";
		boolean success = false;
		try {
			coreParameterService.delCoreParameterById(coreParamId);
			msg = "删除参数成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,删除参数失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	} 
	
	@RequestMapping("/ajaxFindCoreParameterById")
	@ResponseBody
	public Map<String,Object> ajaxFindCoreParameterById(Long coreParamId)throws Exception {
		String msg = "";
		boolean success = false;
		CoreParameter coreParameter = null;
		try {
			coreParameter = coreParameterService.queryCoreParameterById(coreParamId);
			msg = "查找参数成功！";
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "程序发生异常,查找参数失败！ ";
			success = false;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		if(success){
			map.put("data", coreParameter);
		}else{
			map.put("errorMessage", msg);
		}
		map.put("success", success);
		return map;
	} 
	
	@RequestMapping("/coreParameterListJsonData")
	@ResponseBody
	public Map<String,Object> coreParameterListJsonData(HttpServletRequest request)throws Exception {
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String paramName = request.getParameter("paramName");
		CoreParameterExample example = new CoreParameterExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(paramName)){
			criteria.andParamNameLike("%"+paramName.trim()+"%");
		}
		if(StringUtils.isEmpty(start) || StringUtils.equals(start, "0")) {
			start = "1";
		}
		if(StringUtils.isEmpty(limit)) {
			limit = "10";
		}
		List<CoreParameter> coreParameters = null;
		long total = 0;
		PageBean<CoreParameter> pageBean = coreParameterService.pageCoreParametersByExample(example, Integer.parseInt(start.trim()), Integer.parseInt(limit.trim()));
		if(null != pageBean) {
			coreParameters = pageBean.getList();
			total = pageBean.getTotal();
		}else {
			coreParameters = new ArrayList<CoreParameter>();
		}
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("total", total);
		maps.put("rows", coreParameters);
		return maps;
	}	
}
