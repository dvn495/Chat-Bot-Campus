package com.examplewebsocket.websockets.service;

import java.util.Map;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenIAService {

    @Value("${cohere.api.url}")
    private String apiUrl;

    @Value("${cohere.api.key}")
    private String apiKey;

    @Value("${cohere.model.id}")
    private String modelId;

    // @Value("${openai.api.url}")
    // private String apiUrl;

    // @Value("${openai.api.key}")
    // private String apiKey;

    // @Value("${openai.model.id}")
    // private String modelId;

    private final RestTemplate restTemplate = new RestTemplate();
    // private final Map<String, String> responseCache = new java.util.concurrent.ConcurrentHashMap<>();

    public String getCustomGPTResponse(String prompt) {
        if (prompt == null || prompt.trim().isEmpty()) {
            throw new IllegalArgumentException("El prompt no debe estar vacío.");
        }
        
        // Configuración de los encabezados de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Cuerpo de la solicitud en formato JSON, adaptado para Cohere
        String requestBody = "{ \"model\": \"" + modelId + "\", \"prompt\": \"" + prompt + "\", \"max_tokens\": 50, \"temperature\": 0.7, \"return_likelihoods\": \"NONE\" }";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            // Realizar la solicitud a la API de Cohere
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);
            return parseResponse(response.getBody()); // Extraer el texto de la respuesta
        } catch (Exception e) {
            System.err.println("Error al obtener respuesta de Cohere: " + e.getMessage());
            e.printStackTrace();
            return "Error en la respuesta de la IA. Por favor, inténtalo más tarde.";
        }
    }

    // Método para parsear la respuesta y extraer el campo de texto de Cohere
    private String parseResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getString("text"); // Extrae el campo 'text' del JSON de respuesta
        } catch (Exception e) {
            System.err.println("Error al parsear respuesta de Cohere: " + e.getMessage());
            return "Error en la respuesta de la IA. Inténtalo más tarde.";
        }
    }

    // public String getCustomGPTResponse(String userMessage) {

    //     if (responseCache.containsKey(userMessage)) {
    //         return responseCache.get(userMessage);
    //     }

    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_JSON);
    //     headers.setBearerAuth(apiKey);

    //     Map<String, Object> body = Map.of(
    //         "model", modelId,  
    //         "messages", List.of(
    //             // Map.of("role", "system", "content", "Eres Campi, un asistente amigable que ayuda a entender Campuslands. Responde de forma cálida y detallada, pero sin mencionar otras instituciones educativas o comparaciones."),
    //             Map.of("role", "user", "content", userMessage)
    //         ),
    //         "max_tokens", 100, 
    //         "temperature", 0.7,
    //         "return_likelihoods", "NONE"
    //     );


    //     HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
    //     try {
    //         String response = restTemplate.exchange(
    //             apiUrl, 
    //             HttpMethod.POST, 
    //             entity, 
    //             String.class
    //         ).getBody();
            
            
    //         String cohereResponse = parseResponse(response);
    //         responseCache.put(userMessage, cohereResponse);
    //         // responseCache.put(userMessage, response);
    //         // return response;
    //         return cohereResponse;
    //     } catch (Exception e) {
    //         System.err.println("Error al obtener respuesta de OpenAI: " + e.getMessage());
    //         e.printStackTrace();    
    //         return "Error al obtener respuesta de la IA. Inténtalo más tarde.";
    //     }
    // }


    // private String parseResponse(String response) {
    //     try {
    //         org.json.JSONObject jsonResponse = new org.json.JSONObject(response);
    //         return jsonResponse.getString("text");
    //     } catch (Exception e) {
    //         System.err.println("Error al parsear respuesta de Cohere: " + e.getMessage());
    //         return "Error en la respuesta de la IA. Inténtalo más tarde.";
    //     }
    // }


}
