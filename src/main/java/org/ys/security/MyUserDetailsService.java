package org.ys.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.ys.common.constant.CoreMenuContant;
import org.ys.core.model.CoreMenu;
import org.ys.core.model.CoreUser;
import org.ys.core.model.CoreUserExample;
import org.ys.core.service.CoreMenuService;
import org.ys.core.service.CoreUserService;

@Component("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private CoreUserService coreUserService;
	@Autowired
	private CoreMenuService coreMenuService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CoreUser coreUser = null;
		try {
			if(StringUtils.isNotEmpty(username)) {
				CoreUserExample example = new CoreUserExample();
				example.createCriteria().andUserNameEqualTo(username.trim());
				List<CoreUser> coreUserList = coreUserService.queryCoreUsersByExample(example);
				if(null != coreUserList && coreUserList.size() > 0) {
					coreUser = coreUserList.get(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null == coreUser) {
			return null;
		}else {
			List<CoreMenu> coreMenuList = coreMenuService.listCoreMenusByUserId(coreUser.getCoreUserId());
			List<GrantedAuthority> authList = null;
			if(null != coreMenuList && coreMenuList.size() > 0) {
				authList = new ArrayList<>();
				for (CoreMenu coreMenu : coreMenuList) {
					if(CoreMenuContant.MENU_TYPE_PERMISSION.equals(coreMenu.getMenuType()) && StringUtils.isNotEmpty(coreMenu.getPermission())) {
						authList.add(new SimpleGrantedAuthority(coreMenu.getPermission()));
					}
				}
			}
			boolean enable = true;
			if(coreUser.getStatus() == "0") {
				enable = false;
			}
			return new User(coreUser.getUserName(),coreUser.getPassword(),enable,true,true,true,authList);
		}
	}

}
