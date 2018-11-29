package org.ys.core.service;

import org.ys.core.model.CoreUserRole;

public interface CoreUserRoleService {

	public void insertCoreUserRole(CoreUserRole coreUserRole);
	
	public void delCoreUserRoleByUserId(Long coreUserId);
	
	public void delCoreUserRoleByRoleId(Long coreRoleId);
}