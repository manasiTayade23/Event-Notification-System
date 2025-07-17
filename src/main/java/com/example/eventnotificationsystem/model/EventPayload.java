package com.example.eventnotificationsystem.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmailPayload.class, name = "EMAIL"),
        @JsonSubTypes.Type(value = SmsPayload.class, name = "SMS"),
        @JsonSubTypes.Type(value = PushPayload.class, name = "PUSH")
})
public abstract class EventPayload {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}