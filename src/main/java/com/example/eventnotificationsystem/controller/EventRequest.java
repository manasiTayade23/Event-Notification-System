package com.example.eventnotificationsystem.controller;

import com.example.eventnotificationsystem.model.EventType;
import com.example.eventnotificationsystem.model.EventPayload;

public class EventRequest {
    private EventType eventType;
    private EventPayload payload;
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