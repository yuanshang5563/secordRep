package org.ys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
	
    /**
     * 403页面
     */
    @GetMapping(value = "/403")
    public String error_403() {
        return "commons/403";
    }	

    /**
     * 404页面
     */
    @GetMapping(value = "/404")
    public String error_404() {
        return "commons/404";
    }

    /**
     * 500页面
     */
    @GetMapping(value = "/500")
    public String error_500() {
        return "commons/500";
    }

}