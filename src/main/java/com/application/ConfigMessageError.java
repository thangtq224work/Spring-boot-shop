package com.application;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ConfigMessageError {
	@Bean("messageSource") // must start with lower char
	public MessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource(); 
		messageSource.addBasenames("classpath:config/message");
		messageSource.setDefaultEncoding("utf-8");
		return messageSource;
	}
}
