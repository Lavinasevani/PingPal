package com.pingpal.pingpal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // WebSocket endpoint
                .setAllowedOriginPatterns("*") // For dev: allow all origins
                .withSockJS(); // fallback option
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user"); // client subscribes to this
        registry.setApplicationDestinationPrefixes("/app"); // client sends to this
        registry.setUserDestinationPrefix("/user");
    }
}
