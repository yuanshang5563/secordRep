package org.ys.core.service;

import java.util.List;
import java.util.Set;

import org.ys.common.domain.Tree;
import org.ys.common.page.PageBean;
import org.ys.core.model.CoreDictionariesGroup;
import org.ys.core.model.CoreDictionariesGroupExample;

public interface CoreDictionariesGroupService {
	
	public CoreDictionariesGroup queryCoreDictionariesGroupById(Long coreDictGroupId) throws Exception;
	
	public void save(CoreDictionariesGroup coreDictionariesGroup) throws Exception;
	
	public void updateById(CoreDictionariesGroup coreDictionariesGroup) throws Exception;
	
	public void updateByExaple(CoreDictionariesGroup coreDictionariesGroup,CoreDictionariesGroupExample example) throws Exception;
	
	public void delCoreDictionariesGroupById(Long coreDictGroupId) throws Exception;
	
	public List<CoreDictionariesGroup>queryCoreDictionariesGroupsByExample(CoreDictionariesGroupExample example) throws Exception;	
	
	public PageBean<CoreDictionariesGroup> pageCoreDictionariesGroupsByExample(CoreDictionariesGroupExample example,int pageNum,int pageSize) throws Exception;
	
	public Tree<CoreDictionariesGroup> getCoreDictionariesGroupTree(CoreDictionariesGroupExample example) throws Exception;
	
	public List<CoreDictionariesGroup> queryCoreDictionariesGroupsByParentId(Long parentId) throws Exception;
	
	public Set<CoreDictionariesGroup> queryAllSubCoreDictionariesGroupsByDictGroupId(Long coreDictGroupId) throws Exception;
	
}