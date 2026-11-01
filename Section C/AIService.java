
package com.example.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Service
public class AIService {

        @Value("${openai.api.key}")
        private String apiKey;

        public String getRecommendation(String preference) throws Exception {

                ObjectMapper mapper = new ObjectMapper();

                ObjectNode body = mapper.createObjectNode();
                body.put("model", "gpt-4o-mini");

                ArrayNode messages = mapper.createArrayNode();

                ObjectNode message = mapper.createObjectNode();
                message.put("role", "user");
                message.put("content",
                                "Suggest a food meal for this preference: " + preference);

                messages.add(message);
                body.set("messages", messages);

                String json = mapper.writeValueAsString(body);

                HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + apiKey)
                                .POST(HttpRequest.BodyPublishers.ofString(json))
                                .build();

                HttpClient client = HttpClient.newHttpClient();

                HttpResponse<String> response = client.send(
                                request,
                                HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                        throw new RuntimeException("OpenAI API error: " + response.statusCode());
                }

                JsonNode result = mapper.readTree(response.body());

                return result.get("choices")
                                .get(0)
                                .get("message")
                                .get("content")
                                .asText();
        }
}
