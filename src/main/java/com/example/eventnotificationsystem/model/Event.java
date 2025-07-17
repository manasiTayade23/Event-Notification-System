package com.example.eventnotificationsystem.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

public abstract class Event {
    private String eventId;
    private EventType eventType;
    private String callbackUrl;

    // Getters and setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public EventType getEventType() { return eventType; }
    public void setEventType(EventType eventType) { this.eventType = eventType; }
    public String getCallbackUrl() { return callbackUrl; }
    public void setCallbackUrl(String callbackUrl) { this.callbackUrl = callbackUrl; }
}