package com.examplewebsocket.websockets.service;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
<<<<<<< HEAD
import java.net.URI;
=======
>>>>>>> 917286f8dd3028398fe48ee3000059dd55494f32

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.examplewebsocket.websockets.Login.Jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatHandler.class);

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    private final JwtService jwtService;

    @Autowired
    private OpenIAService openIAService;

    private final Map<String, Long> sessionLastRequestTime = new ConcurrentHashMap<>(); 

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Map<String, String> parameters = session.getUri().getQueryParameters();
        String token = parameters.get("token");
        
        // Valida el token y realiza la autenticación
        if (token == null || !jwtService.isTokenValid(token)) {
            session.close();
            return;
        }

        // Procede con la conexión si el token es válido
        super.afterConnectionEstablished(session);
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
        String aiResponse;
        long currentTime = System.currentTimeMillis();
        long lastRequestTime = sessionLastRequestTime.getOrDefault(session.getId(), 0L);

        try {
            String payload = message.getPayload();
            JSONObject jsonMessage;

            try {
                jsonMessage = new JSONObject(payload);
            } catch (Exception e) {
                // Manejo de error si el payload no es un JSON válido
                session.sendMessage(new TextMessage("El formato del mensaje no es válido. Debe ser un JSON con el campo 'message'."));
                return;
            }

            if (currentTime - lastRequestTime < TimeUnit.SECONDS.toMillis(5)) {
                aiResponse = "Por favor, espera unos segundos antes de enviar otro mensaje.";
            } else {
                sessionLastRequestTime.put(session.getId(), currentTime);

                try {
                    String userMessage = jsonMessage.getString("message");
                    aiResponse = openIAService.getCustomGPTResponse(userMessage);
                } catch (Exception e) {
                    LOGGER.error("Error al obtener respuesta de OpenAI: " + e.getMessage(), e);
                    aiResponse = "Error en la respuesta de la IA. Por favor, inténtalo más tarde.";
                }
            }

            session.sendMessage(new TextMessage(aiResponse));

        } catch (IOException e) {
            LOGGER.error("Error al enviar mensaje: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error general en el manejo del mensaje: " + e.getMessage(), e);
        }



        // // for (WebSocketSession webSocketSession : sessions) {
        // //     try {
        // //         webSocketSession.sendMessage(message);
        // //         LOGGER.info("Mensaje recibido del cliente: " + message);
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

