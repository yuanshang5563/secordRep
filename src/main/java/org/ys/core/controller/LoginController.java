package org.ys.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/LoginController")
public class LoginController {
	
	@RequestMapping("/login")
	@ResponseBody
	public Map<String,Object> login(String username,String password){
		String msg = "";
		boolean success = false;
		//try {
			msg = "登陆成功！ ";
			success = true;
		//} catch (AuthenticationException e) {
			//e.printStackTrace();
			//msg = e.getMessage();
		//}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("msg", msg);
		map.put("success", success);
		return map;
	}
	
	@RequestMapping("/logout")
	public String logout(){
		return "login";
	}
	
	@RequestMapping("/fontIcoList")
	public String fontIcoList(){
		return "/commons/fontIcoList";
	}
}
