package com.example.eventnotificationsystem.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SmsPayload extends EventPayload {

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(
        regexp = "^\\+?[0-9]{10,15}$",
        message = "Phone number must be valid and contain 10 to 15 digits (with optional +)"
    )
    private String phoneNumber;
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
} 