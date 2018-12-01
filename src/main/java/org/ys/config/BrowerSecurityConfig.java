package org.ys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.ys.security.MyAuthenticationFailureHandler;
import org.ys.security.MyAuthenticationSuccessHandler;
import org.ys.security.MyUserDetailsService;

@Configuration
//@EnableWebSecurity // 注解开启Spring Security的功能
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
	@Autowired
	private MyUserDetailsService myUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.userDetailsService(myUserDetailsService)
        	.formLogin() 
        		.loginProcessingUrl("/login")
        		.loginPage("/login.html").permitAll()
        		.failureHandler(myAuthenticationFailureHandler)
        		.successHandler(myAuthenticationSuccessHandler)
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .and()
                .and()
                .authorizeRequests()
                .antMatchers("/login.html","/LoginController/**","/login","/css/**","/editor-app/**",
                "/fonts/**","/img/**","/js/**","/utils/**","/favicon.ico")
                .permitAll()       
                .anyRequest()            
                .authenticated()
            .and().logout().permitAll()
            .and()
        .csrf().disable();
    }
}
