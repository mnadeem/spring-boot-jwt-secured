package com.github.nadeem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.nadeem.api.interceptors.RolesAllowedInterceptor;
import com.github.nadeem.api.interceptors.TokenRequiredInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer {

	@Autowired
	private RolesAllowedInterceptor rolesRequiredInterceptor;

	@Autowired
	private TokenRequiredInterceptor tokenRequiredInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(rolesRequiredInterceptor);
		registry.addInterceptor(tokenRequiredInterceptor);
	}
}