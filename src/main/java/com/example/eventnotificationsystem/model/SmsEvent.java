package com.example.eventnotificationsystem.model;

public class SmsEvent extends Event {
    private String phoneNumber;
    private String message;

    public SmsEvent() {
        setEventType(EventType.SMS);
    }

    // Getters and setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}