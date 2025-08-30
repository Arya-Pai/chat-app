package com.project.config;

import com.project.repo.MessageRepo;
import com.project.service.ChatWebSocketHandler;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
public class WebSocketConfig implements WebSocketConfigurer{
	 private final MessageRepo messageRepo;

	    public WebSocketConfig(MessageRepo messageRepo) {
	    	System.out.println("WebSocketConfig initialized, handler mapped to /ws/chat");
	        this.messageRepo = messageRepo;
	    }
	   
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addHandler(new ChatWebSocketHandler(messageRepo), "/ws/chat/").setAllowedOrigins("http://localhost:8080");
		
	}
	
}
