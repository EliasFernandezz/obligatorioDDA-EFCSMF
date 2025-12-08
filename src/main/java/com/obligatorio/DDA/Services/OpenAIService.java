/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.Services;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author mateo
 */

@Service
public class OpenAIService {

    private final WebClient webClient;

    public OpenAIService(@Value("${openai.api.key}") String apiKey) {

        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String consultarIA(String prompt) {

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4.1-mini",
                "input", prompt
        );

        Map response = webClient.post()
                .uri("/responses")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map output = (Map) ((java.util.List) response.get("output")).get(0);

        return (String) output.get("text");
    }
}
