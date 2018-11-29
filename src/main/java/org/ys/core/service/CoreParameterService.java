package org.ys.core.service;

import java.util.List;

import org.ys.common.page.PageBean;
import org.ys.core.model.CoreParameter;
import org.ys.core.model.CoreParameterExample;

public interface CoreParameterService {
	
	public CoreParameter queryCoreParameterById(Long coreParamId) throws Exception;
	
	public void save(CoreParameter coreParameter) throws Exception;
	
	public void updateById(CoreParameter coreParameter) throws Exception;
	
	public void updateByExaple(CoreParameter coreParameter,CoreParameterExample example) throws Exception;
	
	public void delCoreParameterById(Long coreParamId) throws Exception;
	
	public List<CoreParameter>queryCoreParametersByExample(CoreParameterExample example) throws Exception;	
	
	public PageBean<CoreParameter> pageCoreParametersByExample(CoreParameterExample example,int pageNum,int pageSize) throws Exception;
	
}