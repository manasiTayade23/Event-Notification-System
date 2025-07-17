package com.example.eventnotificationsystem.model;

public class EmailPayload extends EventPayload {
    private String recipient;

    @NotBlank(message = "Recipient is required")
    @Email(message = "Invalid email format")
    public String getRecipient() {
        return recipient;
    }
    
    @NotBlank(message = "Recipient is required")
    @Email(message = "Invalid email format")
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
} 