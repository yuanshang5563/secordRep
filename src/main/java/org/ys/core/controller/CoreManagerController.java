package org.ys.core.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ys.common.domain.Tree;
import org.ys.core.model.CoreMenu;
import org.ys.core.model.CoreUser;
import org.ys.core.model.CoreUserExample;
import org.ys.core.service.CoreMenuService;
import org.ys.core.service.CoreUserService;

@Controller
@RequestMapping("/manager/core/CoreManagerController")
public class CoreManagerController {
	@Autowired
	private CoreMenuService coreMenuService;
	
	@Autowired
	private CoreUserService coreUserService;	
	
	@RequestMapping("/main")
	public String main(Model model) throws Exception {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		if(StringUtils.isNotEmpty(username)) {
			CoreUserExample example = new CoreUserExample();
			example.createCriteria().andUserNameEqualTo(username.trim());
			List<CoreUser> users = coreUserService.queryCoreUsersByExample(example);
			CoreUser coreUser = null;
			if(null != users && users.size() > 0) {
				coreUser = users.get(0);
			}
			List<Tree<CoreMenu>> menus = coreMenuService.listMenuTreeByUserId(coreUser.getCoreUserId());
			model.addAttribute("menus", menus);
			model.addAttribute("name", coreUser.getRealName());
			model.addAttribute("picUrl","/img/photo_s.png");
			model.addAttribute("username", coreUser.getUserName());
		}
		
//		List<Tree<CoreMenu>> menus = coreMenuService.listMenuTreeByUserId(1l);
//		model.addAttribute("menus", menus);
//		model.addAttribute("name", "test");
//		model.addAttribute("picUrl","/img/photo_s.jpg");
//		model.addAttribute("username", "test111");
		return "/manager/main";
	}
	
	@RequestMapping("/index")
	public String index(Model model) throws Exception {
		return "/manager/index";
	}
}
