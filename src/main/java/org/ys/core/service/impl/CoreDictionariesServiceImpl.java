package org.ys.core.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ys.common.page.PageBean;
import org.ys.core.dao.CoreDictionariesMapper;
import org.ys.core.model.CoreDictionaries;
import org.ys.core.model.CoreDictionariesExample;
import org.ys.core.service.CoreDictionariesService;

import com.github.pagehelper.PageHelper;

@Service("coreDictionariesService")
public class CoreDictionariesServiceImpl implements CoreDictionariesService {
	
	@Autowired
	private CoreDictionariesMapper coreDictionariesMapper;

	@Override
	public CoreDictionaries queryCoreDictionariesById(Long coreDictId) throws Exception {
		if(null == coreDictId) {
			return null;
		}
		return coreDictionariesMapper.selectByPrimaryKey(coreDictId);
	}

	@Override
	public void save(CoreDictionaries coreDictionaries) throws Exception {
		if(null != coreDictionaries) {
			coreDictionariesMapper.insert(coreDictionaries);
		}
	}

	@Override
	public void updateById(CoreDictionaries coreDictionaries) throws Exception {
		if(null != coreDictionaries && null != coreDictionaries.getCoreDictId()) {
			coreDictionariesMapper.updateByPrimaryKey(coreDictionaries);
		}
	}

	@Override
	public void updateByExaple(CoreDictionaries coreDictionaries, CoreDictionariesExample example) throws Exception {
		if(null != coreDictionaries && null != example) {
			coreDictionariesMapper.updateByExample(coreDictionaries, example);
		}
	}

	@Override
	public void delCoreDictionariesById(Long coreDictId) throws Exception {
		if(null != coreDictId) {
			coreDictionariesMapper.deleteByPrimaryKey(coreDictId);
		}
	}

	@Override
	public List<CoreDictionaries> queryCoreDictionariesByExample(CoreDictionariesExample example) throws Exception {
		if(null == example) {
			return null;
		}
		return coreDictionariesMapper.selectByExample(example);
	}

	@Override
	public PageBean<CoreDictionaries> pageCoreDictionariesByExample(CoreDictionariesExample example, int pageNum, int pageSize) throws Exception {
		if(null == example) {
			return null;
		}
		PageHelper.startPage(pageNum, pageSize, true);
		List<CoreDictionaries> dictList = coreDictionariesMapper.selectByExample(example);
		return new PageBean<CoreDictionaries>(dictList);
	}

	@Override
	public List<CoreDictionaries> listCoreDictionariesByDictGroupId(Long coreDictGroupId) {
		if(null == coreDictGroupId) {
			return null;
		}
		return coreDictionariesMapper.listCoreDictionariesByDictGroupId(coreDictGroupId);
	}

	@Override
	public List<CoreDictionaries> listCoreDictionariesByDictGroupCode(String dictGroupCode) {
		if(StringUtils.isEmpty(dictGroupCode)) {
			return null;
		}
		return coreDictionariesMapper.listCoreDictionariesByDictGroupCode(dictGroupCode);
	}

}
