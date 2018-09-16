package org.ys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/userController")
public class UserController {
	
	@RequestMapping("/addUserPage")
	public ModelAndView addUserPage(){
		return new ModelAndView("/manager/user/addUser");
	}
}
