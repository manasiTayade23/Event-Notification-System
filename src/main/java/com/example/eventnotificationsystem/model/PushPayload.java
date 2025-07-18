package com.example.eventnotificationsystem.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PushPayload extends EventPayload {

    @NotBlank(message = "Device ID cannot be blank")
    @Pattern(
        regexp = "^[a-zA-Z0-9\\-]{5,50}$",
        message = "Device ID must be alphanumeric (with hyphens allowed), 5 to 50 characters long"
    )
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
} 