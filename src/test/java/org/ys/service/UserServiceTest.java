package org.ys.service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.ys.model.User;
import org.ys.model.UserExample;
import org.ys.model.UserExample.Criteria;

import com.github.pagehelper.PageInfo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {
	@Autowired
	private UserService userService;
	
	@Test
	public void testInsert(){
		User user = new User();
		user.setPassword("123");
		user.setPhone("13322221111");
		//user.setUserId(2);
		user.setUserName("test");
		userService.addUser(user);
	}
	
	@Test
	public void testQuery(){
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo("test123");
		PageInfo<User> pageInfo = userService.pageUser(1, 10,example);
		System.out.println("-------- " + pageInfo.getTotal());
		List<User> users = pageInfo.getList();
		if(null != users && users.size() > 0){
			for (User user : users) {
				System.out.println(user.getUserId() + " --------- " + user.getUserName());
			}
		}
	}	
}
