package org.ys.core.service;

import java.util.List;
import java.util.Set;

import org.ys.common.domain.Tree;
import org.ys.common.page.PageBean;
import org.ys.core.model.CoreMenu;
import org.ys.core.model.CoreMenuExample;

public interface CoreMenuService {
	public CoreMenu queryCoreMenuById(Long coreMenuId) throws Exception;
	
	public void save(CoreMenu coreMenu) throws Exception;
	
	public void updateById(CoreMenu coreMenu) throws Exception;
	
	public void updateByExaple(CoreMenu corePermission,CoreMenuExample example) throws Exception;
	
	public void delCoreMenuById(Long coreMenuId) throws Exception;
	
	public List<CoreMenu>queryCoreMenusByExample(CoreMenuExample example) throws Exception;	
	
	public List<CoreMenu> queryCoreMenusByParentId(Long parentId) throws Exception;
	
	public Set<CoreMenu> queryAllSubCoreMenusByMenuId(Long coreMenuId) throws Exception;
	
	public PageBean<CoreMenu> pageCoreMenusByExample(CoreMenuExample example,int pageNum,int pageSize) throws Exception;
	
	public List<Tree<CoreMenu>> listMenuTreeByUserId(Long coreUserId);
	
	public Tree<CoreMenu> getCoreMenuTreeByRoleId(Long coreRoleId);
	
	public Tree<CoreMenu> getCoreMenuTree() throws Exception;
	
	public List<CoreMenu> listCoreMenusByUserId(Long coreUserId);
}