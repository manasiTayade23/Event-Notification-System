package com.example.eventnotificationsystem.service;

import com.example.eventnotificationsystem.model.EmailEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.verify.VerificationTimes.once;
import static org.mockserver.model.HttpRequest.request;

@SpringBootTest(classes = com.example.eventnotificationsystem.EventNotificationSystemApplication.class)
public class EventProcessorTest {

    @Autowired
    private EventService eventService;

    private static ClientAndServer mockServer;

    @BeforeAll
    public static void setup() {
        mockServer = ClientAndServer.startClientAndServer(1080);
    }

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
    public void testEventProcessingAndCallback() throws Exception {
        mockServer.when(request().withMethod("POST").withPath("/callback"))
                .respond(response().withStatusCode(200));

        EmailEvent event = new EmailEvent();
        event.setRecipient("test@example.com");
        event.setMessage("Test message");
        event.setCallbackUrl("http://localhost:1080/callback");
        eventService.submitEvent(event);

        boolean callbackReceived = false;
        for (int i = 0; i < 10; i++) { // wait up to 10 seconds
            if (mockServer.retrieveRecordedRequests(request().withPath("/callback")).length > 0) {
                callbackReceived = true;
                break;
            }
            Thread.sleep(1000);
        }
        mockServer.verify(
        request()
            .withMethod("POST")
            .withPath("/callback"),
        once()
    );
        assertTrue(callbackReceived, "Callback was not received within 10 seconds");
    }
} 