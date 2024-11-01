package com.examplewebsocket.websockets.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GPTNeoService {

    @Value("${huggingface.api.url:https://api-inference.huggingface.co/models/gpt2}")
    private String apiUrl;

    @Value("${huggingface.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getGPTNeoResponse(String userMessage) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(apiKey);

    Map<String, Object> body = Map.of("inputs", userMessage);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

    for (int i = 0; i < 3; i++) {  // Intentará tres veces
        try {
            // Llamada a la API de Hugging Face
            String response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class).getBody();

            // Extrae solo el texto generado (limpiando el JSON)
            // Dependiendo del formato exacto que devuelve la API, ajusta esta parte si es necesario.
            String generatedText = response.substring(response.indexOf("generated_text") + 17);

            return generatedText.trim();
        } catch (HttpServerErrorException e) {
            if (e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
                System.err.println("El modelo está cargando, esperando antes de reintentar...");
                try {
                    Thread.sleep(2000);  // Tiempo de espera antes de reintentar (ajustable)
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            } else {
                System.err.println("Error al obtener respuesta de Hugging Face API: " + e.getMessage());
                e.printStackTrace();
                return "Error al procesar la respuesta con GPT-Neo.";
            }
        } catch (Exception e) {
            System.err.println("Error desconocido: " + e.getMessage());
            e.printStackTrace();
            return "Error al procesar la respuesta con GPT-Neo.";
        }
    }
    return "El modelo aún no está disponible. Por favor, inténtelo más tarde.";
}

}


