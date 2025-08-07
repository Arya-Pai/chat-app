package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class WebMvConfig implements WebMvcConfigurer {
	 @Override
	    public void configureViewResolvers(ViewResolverRegistry registry) {
	        registry.jsp("/WEB-INF/views/", ".jsp");
	    }
}
