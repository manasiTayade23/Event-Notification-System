package com.example.eventnotificationsystem.model;

public class EmailEvent extends Event {
    private String recipient;
    private String message;

    public EmailEvent() {
        setEventType(EventType.EMAIL);
    }

    // Getters and setters
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}