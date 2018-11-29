package org.ys.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ys.common.page.PageBean;
import org.ys.core.dao.CoreParameterMapper;
import org.ys.core.model.CoreParameter;
import org.ys.core.model.CoreParameterExample;
import org.ys.core.service.CoreParameterService;

import com.github.pagehelper.PageHelper;

@Service("coreParameterService")
public class CoreParameterServiceImpl implements CoreParameterService {
	
	@Autowired
	private CoreParameterMapper coreParameterMapper;

	@Override
	public CoreParameter queryCoreParameterById(Long coreParamId) throws Exception {
		if(null == coreParamId) {
			return null;
		}
		return coreParameterMapper.selectByPrimaryKey(coreParamId);
	}

	@Override
	public void save(CoreParameter coreParameter) throws Exception {
		if(null != coreParameter) {
			coreParameterMapper.insert(coreParameter);
		}
	}

	@Override
	public void updateById(CoreParameter coreParameter) throws Exception {
		if(null != coreParameter && null != coreParameter.getCoreParamId()) {
			coreParameterMapper.updateByPrimaryKey(coreParameter);
		}
	}

	@Override
	public void updateByExaple(CoreParameter coreParameter, CoreParameterExample example) throws Exception {
		if(null != coreParameter && null != example) {
			coreParameterMapper.updateByExample(coreParameter, example);
		}
	}

	@Override
	public void delCoreParameterById(Long coreParamId) throws Exception {
		if(null != coreParamId) {
			coreParameterMapper.deleteByPrimaryKey(coreParamId);
		}
	}

	@Override
	public List<CoreParameter> queryCoreParametersByExample(CoreParameterExample example) throws Exception {
		if(null == example) {
			return null;
		}
		return coreParameterMapper.selectByExample(example);
	}

	@Override
	public PageBean<CoreParameter> pageCoreParametersByExample(CoreParameterExample example, int pageNum, int pageSize) throws Exception {
		if(null == example) {
			return null;
		}
		PageHelper.startPage(pageNum, pageSize, true);
		List<CoreParameter> paramList = coreParameterMapper.selectByExample(example);
		return new PageBean<CoreParameter>(paramList);
	}

}
