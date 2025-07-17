package com.example.eventnotificationsystem.service;

import com.example.eventnotificationsystem.model.EmailEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest(classes = com.example.eventnotificationsystem.EventNotificationSystemApplication.class)
public class EventProcessorTest {

    @Autowired
    private EventService eventService;

    private static ClientAndServer mockServer;

    @BeforeAll
    public static void setup() {
        mockServer = ClientAndServer.startClientAndServer(1080);
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

        Thread.sleep(6000); // Wait for processing (5s + buffer)

        assertTrue(mockServer.retrieveRecordedRequests(request().withPath("/callback")).length > 0);
    }
} 