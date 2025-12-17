/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.obligatorio.DDA.Services;

import java.util.List;
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

    public OpenAIService(@Value("${OPENAI_API_KEY}") String apiKey) {

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
                    .bodyToMono(Map.class)
                    .block();

            if (response == null || !response.containsKey("output")) {
                System.out.println("IA sin output");
                return null;
            }

            List<Map<String, Object>> output = (List<Map<String, Object>>) response.get("output");

            Map<String, Object> first = output.get(0);
            List<Map<String, Object>> content =
                    (List<Map<String, Object>>) first.get("content");

            Map<String, Object> textNode = content.get(0);
            String texto = (String) textNode.get("text");

            System.out.println("Respuesta del Juez:");
            System.out.println(texto);

            return texto;

        } catch (Exception e) {
            System.out.println("Error llamando a OpenAI:");
            e.printStackTrace();
            return null;
        }
    }
}
