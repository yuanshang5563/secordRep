package org.ys.core.service;

import java.util.List;
import java.util.Set;

import org.ys.common.domain.Tree;
import org.ys.common.page.PageBean;
import org.ys.core.model.CoreDept;
import org.ys.core.model.CoreDeptExample;

public interface CoreDeptService {
	public CoreDept queryCoreDeptById(Long coreDeptId) throws Exception;
	
	public void save(CoreDept coreDept) throws Exception;
	
	public void updateById(CoreDept coreDept) throws Exception;
	
	public void updateByExaple(CoreDept coreDept,CoreDeptExample example) throws Exception;
	
	public void delCoreDeptById(Long coreDeptId) throws Exception;
	
	public List<CoreDept>queryCoreDeptsByExample(CoreDeptExample example) throws Exception;
	
	public PageBean<CoreDept> pageCoreDeptsByExample(CoreDeptExample example,int pageNum,int pageSize) throws Exception;
	
	public Tree<CoreDept> getCoreDeptTree(CoreDeptExample example) throws Exception;
	
	public List<CoreDept> queryCoreDeptsByParentId(Long parentId) throws Exception;
	
	public Set<CoreDept> queryAllSubCoreDeptsByDeptId(Long coreDeptId) throws Exception;
}