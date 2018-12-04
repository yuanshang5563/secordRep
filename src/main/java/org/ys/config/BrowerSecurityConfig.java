package org.ys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.ys.security.MyAuthenticationFailureHandler;
import org.ys.security.MyAuthenticationSuccessHandler;
import org.ys.security.MyFilterSecurityInterceptor;
import org.ys.security.MyUserDetailsService;

@Configuration
@EnableWebSecurity // 注解开启Spring Security的功能
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
	@Autowired
	private MyUserDetailsService myUserDetailsService;
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;	

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//.userDetailsService(myUserDetailsService)
        	.formLogin() 
        		.loginProcessingUrl("/login")
        		.loginPage("/LoginController/loginPage")
        		.failureHandler(myAuthenticationFailureHandler)
        		.successHandler(myAuthenticationSuccessHandler)
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/LoginController/loginPage")
                .and()
                .and()
                .authorizeRequests()
                .antMatchers("/login.html","/LoginController/**","/login","favicon.ico","/css/**",
                "/editor-app/**","/fonts/**","/img/**","/js/**","/utils/**")
                .permitAll()   
                //.anyRequest().access("@rbacService.hasPermission(request,authentication)")
                .anyRequest().authenticated()
            .and().logout().logoutUrl("/logout").logoutSuccessUrl("/LoginController/loginPage")
            .and()
            .headers().frameOptions().disable()
            .and()
            .csrf().disable();
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }

//	@Bean
//	public static NoOpPasswordEncoder passwordEncoder() {
//	    return NoOpPasswordEncoder.getInstance();
//	}    

	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}   
	
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }	
    
}
