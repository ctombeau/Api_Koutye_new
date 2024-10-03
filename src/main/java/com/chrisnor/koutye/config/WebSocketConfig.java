package com.chrisnor.koutye.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/channel");
		config.setApplicationDestinationPrefixes("/app");
	}

	
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ichat").setAllowedOrigins("*");
	}
}
