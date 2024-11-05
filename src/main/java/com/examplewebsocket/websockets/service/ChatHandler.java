package com.examplewebsocket.websockets.service;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Service
public class ChatHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatHandler.class);

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Autowired
    private OpenIAService openIAService;

    private final Map<String, Long> sessionLastRequestTime = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        LOGGER.info("Cliente conectado: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        LOGGER.info("Cliente desconectado: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message){

        String userMessage = message.getPayload();
        String aiResponse;

        long currentTime = System.currentTimeMillis();
        long lastRequestTime = sessionLastRequestTime.getOrDefault(session.getId(), 0L);


        if (currentTime - lastRequestTime < TimeUnit.SECONDS.toMillis(5)) {

            aiResponse = "Por favor, espera unos segundos antes de enviar otro mensaje.";

        } else {
            
            sessionLastRequestTime.put(session.getId(), currentTime);

            try {
                aiResponse = openIAService.getCustomGPTResponse(userMessage);
            } catch (Exception e) {
                System.err.println("Error al obtener respuesta de OpenAI: " + e.getMessage());
                e.printStackTrace();
                aiResponse = "Error en la respuesta de la IA. Por favor, inténtalo más tarde.";
            }
        }

        try {
            session.sendMessage(new TextMessage(aiResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // // for (WebSocketSession webSocketSession : sessions) {
        // //     try {
        // //         webSocketSession.sendMessage(message);
        // //         LOGGER.info("Mensaje recibido del cliente: " + message);

        // //     } catch (IOException e) {
        // //         LOGGER.error(e.getMessage(), e);
        // //     }
        // // }
    }

        
}

