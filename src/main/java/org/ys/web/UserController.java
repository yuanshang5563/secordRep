package org.ys.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.ys.model.User;
import org.ys.service.UserService;

@Controller
@RequestMapping("/userController")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/addUserPage")
	public ModelAndView addUserPage(){
		return new ModelAndView("/manager/user/addUser");
	}
	
	@RequestMapping("/addUser")
	@ResponseBody
	public String addUser(Model model){
		User user = new User();
		userService.addUser(user);
		return "";
	}
}
