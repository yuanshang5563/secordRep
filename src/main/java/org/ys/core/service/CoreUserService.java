package org.ys.core.service;

import java.util.List;

import org.ys.common.page.PageBean;
import org.ys.core.model.CoreUser;
import org.ys.core.model.CoreUserExample;

public interface CoreUserService {
	public CoreUser queryCoreUserById(Long coreUserId) throws Exception;
	
	public void save(CoreUser coreUser) throws Exception;
	
	public void updateById(CoreUser coreUser) throws Exception;
	
	public void updateByExaple(CoreUser coreUser,CoreUserExample example) throws Exception;
	
	public void delCoreUserById(Long coreUserId) throws Exception;
	
	public List<CoreUser>queryCoreUsersByExample(CoreUserExample example) throws Exception;
	
	public PageBean<CoreUser> pageCoreUsersByExample(CoreUserExample example,int pageNum,int pageSize) throws Exception;
	
	public void saveOrUpdateCoreUserAndRoles(CoreUser coreUser,String[] roles) throws Exception;
}