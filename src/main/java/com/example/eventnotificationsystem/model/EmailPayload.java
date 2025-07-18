package com.example.eventnotificationsystem.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailPayload extends EventPayload {

    @NotBlank(message = "Recipient is required")
    @Email(message = "Invalid email format")
    private String recipient;


    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
} 