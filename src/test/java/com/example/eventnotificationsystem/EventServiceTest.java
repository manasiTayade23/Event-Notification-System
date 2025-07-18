package com.example.eventnotificationsystem.service;

import com.example.eventnotificationsystem.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = com.example.eventnotificationsystem.EventNotificationSystemApplication.class)
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @BeforeEach
    public void resetService() throws Exception {
        eventService.getEmailQueue().clear();
        eventService.getSmsQueue().clear();
        eventService.getPushQueue().clear();
        java.lang.reflect.Field field = eventService.getClass().getDeclaredField("acceptingEvents");
        field.setAccessible(true);
        field.set(eventService, true);
    }

    @Test
    public void testSubmitEmailEvent() {
        EmailEvent event = new EmailEvent();
        event.setRecipient("test@example.com");
        event.setMessage("Test message");
        event.setCallbackUrl("http://localhost/callback");
        EventResponse response = eventService.submitEvent(event);
        assertNotNull(response.getEventId());
        assertEquals("Event accepted for processing.", response.getMessage());
    }

    @Test
    public void testSubmitSmsEvent() {
        SmsEvent event = new SmsEvent();
        event.setPhoneNumber("+1234567890");
        event.setMessage("Test SMS");
        event.setCallbackUrl("http://localhost/callback");
        EventResponse response = eventService.submitEvent(event);
        assertNotNull(response.getEventId());
        assertEquals("Event accepted for processing.", response.getMessage());
        // Note: We don't check if queue is empty because EventProcessor consumes events immediately
    }

    @Test
    public void testSubmitPushEvent() {
        PushEvent event = new PushEvent();
        event.setDeviceId("abc-123");
        event.setMessage("Test Push");
        event.setCallbackUrl("http://localhost/callback");
        EventResponse response = eventService.submitEvent(event);
        assertNotNull(response.getEventId());
        assertEquals("Event accepted for processing.", response.getMessage());
        // Note: We don't check if queue is empty because EventProcessor consumes events immediately
    }

    @Test
    public void testShutdown() {
        eventService.stopAcceptingEvents();
        EmailEvent event = new EmailEvent();
        event.setRecipient("test@example.com");
        event.setMessage("Test message");
        event.setCallbackUrl("http://localhost/callback");
        assertThrows(IllegalStateException.class, () -> eventService.submitEvent(event));
    }
}