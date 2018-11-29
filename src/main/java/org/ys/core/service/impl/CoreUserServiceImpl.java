package org.ys.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ys.common.page.PageBean;
import org.ys.core.dao.CoreUserMapper;
import org.ys.core.dao.CoreUserRoleMapper;
import org.ys.core.model.CoreUser;
import org.ys.core.model.CoreUserExample;
import org.ys.core.model.CoreUserRole;
import org.ys.core.service.CoreUserService;

import com.github.pagehelper.PageHelper;

@Service("coreUserService")
public class CoreUserServiceImpl implements CoreUserService {
	@Autowired
	private CoreUserMapper coreUserMapper;
	
	@Autowired
	private CoreUserRoleMapper coreUserRoleMapper;

	@Override
	public CoreUser queryCoreUserById(Long coreUserId) throws Exception {
		if(null == coreUserId) {
			return null;
		}
		return coreUserMapper.selectByPrimaryKey(coreUserId);
	}

	@Override
	public void save(CoreUser coreUser) throws Exception {
		if(null != coreUser) {
			coreUserMapper.insert(coreUser);
		}
	}

	@Override
	public void updateById(CoreUser coreUser) throws Exception {
		if(null != coreUser) {
			coreUserMapper.updateByPrimaryKey(coreUser);
		}
	}

	@Override
	public void updateByExaple(CoreUser coreUser, CoreUserExample example) throws Exception {
		if(null != coreUser && null != example) {
			coreUserMapper.updateByExample(coreUser, example);
		}
	}

	@Override
	public void delCoreUserById(Long coreUserId) throws Exception {
		if(null != coreUserId) {
			coreUserMapper.deleteByPrimaryKey(coreUserId);
		}
	}

	@Override
	public List<CoreUser> queryCoreUsersByExample(CoreUserExample example) throws Exception {
		if(null == example) {
			return null;
		}
		return coreUserMapper.selectByExample(example);
	}

	@Override
	public PageBean<CoreUser> pageCoreUsersByExample(CoreUserExample example, int pageNum, int pageSize) throws Exception {
		if(null == example) {
			return null;
		}
		PageHelper.startPage(pageNum, pageSize, true);
		List<CoreUser> userList = coreUserMapper.selectByExample(example);
		return new PageBean<CoreUser>(userList);
	}

	@Override
	public void saveOrUpdateCoreUserAndRoles(CoreUser coreUser, String[] roles) throws Exception {
		if(null != coreUser) {
			if(null == coreUser.getCoreUserId() || coreUser.getCoreUserId() == 0) {
				coreUserMapper.insert(coreUser);
			}else {
				coreUser.setModifiedTime(new Date());
				coreUserMapper.updateByPrimaryKey(coreUser);
			}
			//更新角色映射
			if(null != roles && roles.length > 0) {
				coreUserRoleMapper.delCoreUserRoleByUserId(coreUser.getCoreUserId());
				for (String coreRoleId : roles) {
					CoreUserRole coreUserRole = new CoreUserRole();
					coreUserRole.setCoreRoleId(Long.parseLong(coreRoleId));
					coreUserRole.setCoreUserId(coreUser.getCoreUserId());
					coreUserRoleMapper.insertCoreUserRole(coreUserRole);
				}				
			}
		}
	}

}
