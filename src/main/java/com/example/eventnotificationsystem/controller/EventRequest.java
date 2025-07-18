package com.example.eventnotificationsystem.controller;

import com.example.eventnotificationsystem.model.EventType;
import com.example.eventnotificationsystem.model.EventPayload;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EventRequest {
    @NotNull(message = "Event type is required")
    private EventType eventType;
    
    @Valid
    @NotNull(message = "Payload is required")
    private EventPayload payload;
    
    @NotBlank(message = "Callback URL is required")
    private String callbackUrl;

    // Getters and setters
    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventPayload getPayload() {
        return payload;
    }

    public void setPayload(EventPayload payload) {
        this.payload = payload;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}