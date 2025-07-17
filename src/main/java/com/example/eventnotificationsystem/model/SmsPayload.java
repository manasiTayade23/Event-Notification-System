package com.example.eventnotificationsystem.model;

public class SmsPayload extends EventPayload {
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
} 