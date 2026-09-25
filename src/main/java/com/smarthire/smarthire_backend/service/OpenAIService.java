package com.smarthire.smarthire_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model:gpt-5.6-luna}")
    private String model;

    private final HttpClient httpClient =
            HttpClient.newHttpClient();

    public String generateQuestion(
            String role,
            String difficulty,
            String topic) {

        String prompt =
                "Generate one interview question for a candidate. " +
                "Role: " + role + ". " +
                "Difficulty: " + difficulty + ". " +
                "Topic: " + topic + ". " +
                "Return only the interview question. " +
                "Do not include numbering or explanations.";

        String jsonBody =
                "{"
                + "\"model\":\"" + escapeJson(model) + "\","
                + "\"input\":\"" + escapeJson(prompt) + "\""
                + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://api.openai.com/v1/responses"
                ))
                .header(
                        "Content-Type",
                        "application/json"
                )
                .header(
                        "Authorization",
                        "Bearer " + apiKey
                )
                .POST(
                        HttpRequest.BodyPublishers.ofString(
                                jsonBody
                        )
                )
                .build();

        try {

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() < 200
                    || response.statusCode() >= 300) {

                throw new RuntimeException(
                        "OpenAI API error: "
                                + response.body()
                );
            }

            return extractOutputText(
                    response.body()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to generate AI question: "
                            + e.getMessage(),
                    e
            );
        }
    }

    private String extractOutputText(
            String response) {

        String marker =
                "\"type\":\"output_text\"";

        int markerIndex =
                response.indexOf(marker);

        if (markerIndex == -1) {
            return response;
        }

        int textIndex =
                response.indexOf(
                        "\"text\":\"",
                        markerIndex
                );

        if (textIndex == -1) {
            return response;
        }

        int start =
                textIndex + "\"text\":\"".length();

        int end = start;

        while (end < response.length()) {

            if (response.charAt(end) == '"'
                    && response.charAt(end - 1) != '\\') {
                break;
            }

            end++;
        }

        return response
                .substring(start, end)
                .replace("\\\"", "\"")
                .replace("\\n", "\n")
                .replace("\\\\", "\\");
    }

    private String escapeJson(String value) {

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
