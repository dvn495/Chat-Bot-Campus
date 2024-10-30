package com.examplewebsocket.websockets.service;

import java.util.HashMap;
import java.util.Map;

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

    public String getCustomGPTResponse(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", modelId);
        body.put("prompt", prompt);
        body.put("max_tokens", 150);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(
            apiUrl, 
            HttpMethod.POST, 
            entity, 
            String.class).getBody();

    }


}
