package org.ys.core.service;

import org.ys.core.model.CoreRoleMenu;

public interface CoreRoleMenuService {

	public void insertCoreRoleMenu(CoreRoleMenu coreRoleMenu);
	
	public void delCoreRoleMenuByMenuId(Long coreMenuId);
	
	public void delCoreRoleMenuByRoleId(Long coreRoleId);
}