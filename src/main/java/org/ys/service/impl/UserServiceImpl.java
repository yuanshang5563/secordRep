package org.ys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ys.dao.UserMapper;
import org.ys.model.User;
import org.ys.model.UserExample;
import org.ys.service.UserService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public int addUser(User user) {
		if(null != user){
			return userMapper.insert(user);
		}
		return 0;
	}

	@Override
	public PageInfo<User> pageUser(int pageNum, int pageSize,UserExample example) {
		PageHelper.startPage(pageNum, pageSize,true);

		List<User> userList = userMapper.selectByExample(example);
		PageInfo<User> pageInfo = new PageInfo<User>(userList);
		return pageInfo;
	}

}
