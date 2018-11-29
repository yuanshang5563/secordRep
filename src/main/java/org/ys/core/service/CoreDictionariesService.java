package org.ys.core.service;

import java.util.List;

import org.ys.common.page.PageBean;
import org.ys.core.model.CoreDictionaries;
import org.ys.core.model.CoreDictionariesExample;

public interface CoreDictionariesService {
	
	public CoreDictionaries queryCoreDictionariesById(Long coreDictId) throws Exception;
	
	public void save(CoreDictionaries coreDictionaries) throws Exception;
	
	public void updateById(CoreDictionaries coreDictionaries) throws Exception;
	
	public void updateByExaple(CoreDictionaries coreDictionaries,CoreDictionariesExample example) throws Exception;
	
	public void delCoreDictionariesById(Long coreDictId) throws Exception;
	
	public List<CoreDictionaries>queryCoreDictionariesByExample(CoreDictionariesExample example) throws Exception;	
	
	public PageBean<CoreDictionaries> pageCoreDictionariesByExample(CoreDictionariesExample example,int pageNum,int pageSize) throws Exception;
	
	public List<CoreDictionaries> listCoreDictionariesByDictGroupId(Long coreDictGroupId);
	
	public List<CoreDictionaries> listCoreDictionariesByDictGroupCode(String dictGroupCode);
}