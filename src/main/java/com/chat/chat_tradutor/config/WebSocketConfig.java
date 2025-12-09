package com.chat.chat_tradutor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer { // 1. Cabeçalho da classe

    // 2. Método essencial para configurar o broker de mensagens
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); 
        config.setApplicationDestinationPrefixes("/app");
    }

    // 3. Método essencial para registrar o endpoint de conexão
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws"); 
    }
    
    // NENHUM outro método (como configureMessageConverters, configureClientInboundChannel, etc.)
}