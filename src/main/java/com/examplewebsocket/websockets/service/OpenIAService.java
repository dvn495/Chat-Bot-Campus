package com.examplewebsocket.websockets.service;

import java.util.Map;
import java.util.List;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenIAService {

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model.id}")
    private String modelId;

    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, String> responseCache = new java.util.concurrent.ConcurrentHashMap<>();


    public String getCustomGPTResponse(String userMessage) {

        if (responseCache.containsKey(userMessage)) {
            return responseCache.get(userMessage);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
            "model", modelId,  
            "messages", List.of(
                Map.of("role", "system", "content", "Eres Campi, un asistente amigable que ayuda a entender Campuslands. Responde de forma cálida y detallada, pero sin mencionar otras instituciones educativas o comparaciones."),
                Map.of("role", "user", "content", userMessage)
            ),
            "max_tokens", 100, 
            "temperature", 0.7,
            "return_likelihoods", "NONE"
        );


        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        try {
            String response = restTemplate.exchange(
                apiUrl, 
                HttpMethod.POST, 
                entity, 
                String.class
            ).getBody();
            
            
            String cohereResponse = parseResponse(response);
            responseCache.put(userMessage, cohereResponse);
            // responseCache.put(userMessage, response);
            // return response;
            return cohereResponse;
        } catch (Exception e) {
            System.err.println("Error al obtener respuesta de OpenAI: " + e.getMessage());
            e.printStackTrace();    
            return "Error al obtener respuesta de la IA. Inténtalo más tarde.";
        }
    }


    private String parseResponse(String response) {
        try {
            org.json.JSONObject jsonResponse = new org.json.JSONObject(response);
            return jsonResponse.getString("text");
        } catch (Exception e) {
            System.err.println("Error al parsear respuesta de Cohere: " + e.getMessage());
            return "Error en la respuesta de la IA. Inténtalo más tarde.";
        }
    }


}
