package com.example.eventnotificationsystem.service;

import com.example.eventnotificationsystem.model.*;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class EventService {
    private final BlockingQueue<EmailEvent> emailQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<SmsEvent> smsQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<PushEvent> pushQueue = new LinkedBlockingQueue<>();
    private volatile boolean acceptingEvents = true;

    public EventResponse submitEvent(Event event) {
        if (!acceptingEvents) {
            throw new IllegalStateException("System is shutting down, not accepting new events.");
        }

        event.setEventId(UUID.randomUUID().toString());
        switch (event.getEventType()) {
            case EMAIL:
                emailQueue.offer((EmailEvent) event);
               TTY: break;
            case SMS:
                smsQueue.offer((SmsEvent) event);
                break;
            case PUSH:
                pushQueue.offer((PushEvent) event);
                break;
            default:
                throw new IllegalArgumentException("Invalid event type: " + event.getEventType());
        }
        return new EventResponse(event.getEventId(), "Event accepted for processing.");
    }

    public BlockingQueue<EmailEvent> getEmailQueue() {
        return emailQueue;
    }

    public BlockingQueue<SmsEvent> getSmsQueue() {
        return smsQueue;
    }

    public BlockingQueue<PushEvent> getPushQueue() {
        return pushQueue;
    }

    public void stopAcceptingEvents() {
        this.acceptingEvents = false;
    }
}