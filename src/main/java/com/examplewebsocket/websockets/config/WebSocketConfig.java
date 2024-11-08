package com.examplewebsocket.websockets.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.examplewebsocket.websockets.service.ChatHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
<<<<<<< HEAD
        registry.addHandler(chatHandler, "/chat")
            .setAllowedOrigins("*")
            .addInterceptors(new HttpSessionHandshakeInterceptor());;
=======
<<<<<<< HEAD
        registry.addHandler(chatHandler, "/chat")
            .setAllowedOrigins("*")
            .addInterceptors(new HttpSessionHandshakeInterceptor());;
=======
        registry.addHandler(chatHandler, "/chat").setAllowedOrigins("*");
>>>>>>> 917286f8dd3028398fe48ee3000059dd55494f32
>>>>>>> 8a9943e32de06103e4514bfa4ac1298625bee770
    }
}
