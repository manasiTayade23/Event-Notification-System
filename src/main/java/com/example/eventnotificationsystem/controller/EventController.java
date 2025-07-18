package com.example.eventnotificationsystem.controller;

import com.example.eventnotificationsystem.model.*;
import com.example.eventnotificationsystem.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping("/api/events")
    public ResponseEntity<EventResponse> submitEvent(@Valid @RequestBody EventRequest eventRequest) {
        try {
            Event event = validateAndConvertEvent(eventRequest);
            EventResponse response = eventService.submitEvent(event);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new EventResponse(null, e.getMessage()));
        }
    }

    @PostMapping("/callback")
    public ResponseEntity<String> receiveCallback(@RequestBody CallbackRequest callbackRequest) {
        System.out.println("=== CALLBACK RECEIVED ===");
        System.out.println("Event ID: " + callbackRequest.getEventId());
        System.out.println("Status: " + callbackRequest.getStatus());
        System.out.println("Event Type: " + callbackRequest.getEventType());
        System.out.println("Processed At: " + callbackRequest.getProcessedAt());
        if (callbackRequest.getErrorMessage() != null) {
            System.out.println("Error Message: " + callbackRequest.getErrorMessage());
        }
        System.out.println("=========================");
        
        return ResponseEntity.ok("Callback received successfully");
    }

    private Event validateAndConvertEvent(EventRequest eventRequest) {
        if (eventRequest.getEventType() == null || eventRequest.getPayload() == null || eventRequest.getCallbackUrl() == null) {
            throw new IllegalArgumentException("Missing required fields");
        }

        Event event;
        switch (eventRequest.getEventType()) {
            case EMAIL:
                EmailPayload emailPayload = (EmailPayload) eventRequest.getPayload();
                if (emailPayload.getRecipient() == null || emailPayload.getRecipient().isEmpty() ||
                    emailPayload.getMessage() == null || emailPayload.getMessage().isEmpty()) {
                    throw new IllegalArgumentException("Missing required fields");
                }
                EmailEvent emailEvent = new EmailEvent();
                emailEvent.setRecipient(emailPayload.getRecipient());
                emailEvent.setMessage(emailPayload.getMessage());
                event = emailEvent;
                break;
            case SMS:
                SmsPayload smsPayload = (SmsPayload) eventRequest.getPayload();
                if (smsPayload.getPhoneNumber() == null || smsPayload.getPhoneNumber().isEmpty() ||
                    smsPayload.getMessage() == null || smsPayload.getMessage().isEmpty()) {
                    throw new IllegalArgumentException("Missing required fields");
                }
                SmsEvent smsEvent = new SmsEvent();
                smsEvent.setPhoneNumber(smsPayload.getPhoneNumber());
                smsEvent.setMessage(smsPayload.getMessage());
                event = smsEvent;
                break;
            case PUSH:
                PushPayload pushPayload = (PushPayload) eventRequest.getPayload();
                if (pushPayload.getDeviceId() == null || pushPayload.getDeviceId().isEmpty() ||
                    pushPayload.getMessage() == null || pushPayload.getMessage().isEmpty()) {
                    throw new IllegalArgumentException("Missing required fields");
                }
                PushEvent pushEvent = new PushEvent();
                pushEvent.setDeviceId(pushPayload.getDeviceId());
                pushEvent.setMessage(pushPayload.getMessage());
                event = pushEvent;
                break;
            default:
                throw new IllegalArgumentException("Invalid event type: " + eventRequest.getEventType());
        }
        event.setCallbackUrl(eventRequest.getCallbackUrl());
        return event;
    }
}