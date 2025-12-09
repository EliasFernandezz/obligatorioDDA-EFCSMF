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

        try {
            Map response = webClient.post()
                    .uri("/responses")
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .map(body -> new RuntimeException("Error IA: " + body))
                    )
                    .bodyToMono(Map.class)
                    .block();

            if (response == null || !response.containsKey("output")) {
                return null; // <- devolución segura
            }

            java.util.List outputList = (java.util.List) response.get("output");

            if (outputList.isEmpty()) {
                return null;
            }

            Map output = (Map) outputList.get(0);

            return (String) output.get("text");

        } catch (Exception e) {
            System.out.println("Error llamando a OpenAI: " + e.getMessage());
            return null; // <- NUNCA CRASHEA
        }
    }
}
