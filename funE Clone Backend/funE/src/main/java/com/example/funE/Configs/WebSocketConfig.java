package com.example.funE.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getTextSocketHandler(), "/livestream").setAllowedOrigins("/*");
//        registry.addHandler(getBinarySocketHandler(), "/livestream").setAllowedOrigins("/*");
    }

    @Bean
    TextSocketHandler getTextSocketHandler() {
        return new TextSocketHandler();
    }

//    @Bean
//    BinarySocketHandler getBinarySocketHandler() {
//        return new BinarySocketHandler();
//    }
}
