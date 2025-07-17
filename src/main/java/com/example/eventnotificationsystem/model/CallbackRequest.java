package com.example.eventnotificationsystem.model;

import java.time.ZonedDateTime;

public class CallbackRequest {
    private String eventId;
    private String status;
    private String eventType;
    private String errorMessage;
    private String processedAt;

    public CallbackRequest(String eventId, String status, String eventType, String errorMessage, String processedAt) {
        this.eventId = eventId;
        this.status = status;
        this.eventType = eventType;
        this.errorMessage = errorMessage;
        this.processedAt = processedAt;
    }

    // Getters and setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public String getProcessedAt() { return processedAt; }
    public void setProcessedAt(String processedAt) { this.processedAt = processedAt; }
}