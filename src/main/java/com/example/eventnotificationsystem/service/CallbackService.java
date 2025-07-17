package com.example.eventnotificationsystem.service;

import com.example.eventnotificationsystem.model.CallbackRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CallbackService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendCallback(String callbackUrl, CallbackRequest callbackRequest) {
        try {
            String json = objectMapper.writeValueAsString(callbackRequest);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(callbackUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            // Log error (in production, use a proper logging framework)
            System.err.println("Callback failed for URL " + callbackUrl + ": " + e.getMessage());
        }
    }
}