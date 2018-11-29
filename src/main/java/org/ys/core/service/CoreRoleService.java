package org.ys.core.service;

import java.util.List;

import org.ys.common.page.PageBean;
import org.ys.core.model.CoreRole;
import org.ys.core.model.CoreRoleExample;

public interface CoreRoleService {
	public CoreRole queryCoreRoleById(Long coreRoleId) throws Exception;
	
	public void save(CoreRole coreRole) throws Exception;
	
	public void updateById(CoreRole coreRole) throws Exception;
	
	public void updateByExaple(CoreRole coreRole,CoreRoleExample example) throws Exception;
	
	public void delCoreRoleById(Long coreRoleId) throws Exception;
	
	public List<CoreRole>queryCoreRolesByExample(CoreRoleExample example) throws Exception;	
	
	public PageBean<CoreRole> pageCoreRolesByExample(CoreRoleExample example,int pageNum,int pageSize) throws Exception;
	
	public void saveOrUpdateCoreRoleAndCoreMenu(CoreRole coreRole,String[] coreMenuIdArr) throws Exception;
	
	public List<CoreRole> listCoreRolesByUserId(Long coreUserId);
}