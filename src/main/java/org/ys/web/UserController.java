package org.ys.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.ys.common.utils.RequsetUtils;
import org.ys.model.User;
import org.ys.model.UserExample;
import org.ys.model.UserExample.Criteria;
import org.ys.service.UserService;

import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/userController")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/userForm")
	public ModelAndView userForm(){
		return new ModelAndView("/manager/user/user_form");
	}
	
	@RequestMapping("/saveUserForm")
	@ResponseBody
	public Map<String,Object> saveUserForm(HttpServletRequest request){
		String msg = null;
		int result = 0;
		try {
			Map<String,Object> paramMap = RequsetUtils.getParamsMap(request);
			User user = new User();
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			BeanUtils.populate(user, paramMap);		
			userService.addUser(user);
			msg = "增加成功！";
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
			msg = "增加失败！";
		} 
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("states", result);
		map.put("msg", msg);
		return map;
	}
	
	@RequestMapping("/userList")
	public ModelAndView userList(HttpServletRequest request){
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		
		StringBuffer sb = new StringBuffer();
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotEmpty(userName)){
			criteria.andUserNameLike(userName.trim());
			sb.append("&userName=").append(userName.trim());
		}
		if(StringUtils.isNotEmpty(phone)){
			criteria.andPhoneLike(phone.trim());
			sb.append("&phone=").append(phone.trim());
		}
		if(StringUtils.isEmpty(pageNum)){
			pageNum = "1";
		}
		if(StringUtils.isEmpty(pageSize)){
			pageSize = "10";
		}
		PageInfo<User> pageInfo = userService.pageUser(Integer.parseInt(pageNum), Integer.parseInt(pageSize),example);
		ModelAndView model = new ModelAndView("/manager/user/user_list");
		model.addObject("pageInfo", pageInfo);
		model.addObject("sb", sb);
		model.addObject("userName", userName);
		model.addObject("phone", phone);
		return model;
	}
}
