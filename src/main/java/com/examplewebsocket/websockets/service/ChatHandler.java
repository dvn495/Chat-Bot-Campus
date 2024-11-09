package com.examplewebsocket.websockets.service;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatHandler.class);

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final Map<String, Long> sessionLastRequestTime = new ConcurrentHashMap<>();

    @Autowired
    private OpenIAService openIAService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        sessions.add(session);
        LOGGER.info("Cliente conectado: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        LOGGER.info("Cliente desconectado: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        long currentTime = System.currentTimeMillis();
        long lastRequestTime = sessionLastRequestTime.getOrDefault(session.getId(), 0L);
        String aiResponse;

        try {
            String payload = message.getPayload();
            JSONObject jsonMessage = new JSONObject(payload);
            
            if (currentTime - lastRequestTime < TimeUnit.SECONDS.toMillis(5)) {
                aiResponse = "Por favor, espera unos segundos antes de enviar otro mensaje.";
            } else {
                sessionLastRequestTime.put(session.getId(), currentTime);
                String userMessage = jsonMessage.getString("message");
                aiResponse = openIAService.getCustomGPTResponse(userMessage);
            }

            session.sendMessage(new TextMessage(aiResponse));
        } catch (IOException e) {
            LOGGER.error("Error al enviar mensaje: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error general en el manejo del mensaje: " + e.getMessage(), e);
            try {
                session.sendMessage(new TextMessage("El formato del mensaje no es válido. Debe ser un JSON con el campo 'message'."));
            } catch (IOException ioException) {
                LOGGER.error("Error al enviar mensaje de error: " + ioException.getMessage(), ioException);
            }
        }
    }
}
