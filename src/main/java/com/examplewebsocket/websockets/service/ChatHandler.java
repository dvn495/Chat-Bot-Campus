package com.examplewebsocket.websockets.service;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

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

        // Llama al servicio para obtener una respuesta de GPT personalizado
        String aiResponse = openIAService.getCustomGPTResponse(userMessage);

        try {
            // Envía la respuesta de la IA de vuelta al usuario
            session.sendMessage(new TextMessage("IA: " + aiResponse));
        } catch (IOException e) {
            LOGGER.error("Error enviando el mensaje de respuesta de IA", e);
        }

        // for (WebSocketSession webSocketSession : sessions) {
        //     try {
        //         webSocketSession.sendMessage(message);
        //         LOGGER.info("Mensaje recibido del cliente: " + message);

        //     } catch (IOException e) {
        //         LOGGER.error(e.getMessage(), e);
        //     }
        // }
    }
}
