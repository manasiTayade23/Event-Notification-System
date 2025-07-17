package com.example.eventnotificationsystem.model;

public class PushEvent extends Event {
    private String deviceId;
    private String message;

    public PushEvent() {
        setEventType(EventType.PUSH);
    }

    // Getters and setters
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}