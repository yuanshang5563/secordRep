package org.ys.service;

import org.ys.model.User;
import org.ys.model.UserExample;

import com.github.pagehelper.PageInfo;

public interface UserService {
    int addUser(User user);

    PageInfo<User> pageUser(int pageNum, int pageSize,UserExample example);
}
