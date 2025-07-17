package com.example.eventnotificationsystem.model;

public class PushPayload extends EventPayload {
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
} 