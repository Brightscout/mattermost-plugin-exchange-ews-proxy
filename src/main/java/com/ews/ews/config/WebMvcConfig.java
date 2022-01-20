package com.ews.ews.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ews.ews.middleware.AuthMiddleware;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${app.secretAPIKey}")
	private String secretAPIKey;
	
	@Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new AuthMiddleware(this.secretAPIKey));
    }
}
