package com.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.application.interceptor.AccountManagerInterceptor;
import com.application.interceptor.AdminInterceptor;
import com.application.interceptor.LoginInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Autowired
	private AdminInterceptor adminInterceptor;
	@Autowired
	private LoginInterceptor loginInterceptor;
	@Autowired
	private AccountManagerInterceptor accountManagerInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**");
		registry.addInterceptor(accountManagerInterceptor).addPathPatterns("/admin/accounts/**", "/admin/account/**");
		registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/register-success",
				"/register-infor", "/register/**", "/register", "/home/**", "/home", "/access-denied", "/login",
				"/css/**", "/js/**", "/image/**");
	}
}
