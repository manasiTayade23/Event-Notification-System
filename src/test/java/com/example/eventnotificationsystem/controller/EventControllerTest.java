package com.example.eventnotificationsystem.controller;

import com.example.eventnotificationsystem.model.*;
import com.example.eventnotificationsystem.controller.EventRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.example.eventnotificationsystem.EventNotificationSystemApplication.class)
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testValidEventSubmission() throws Exception {
        EventRequest request = new EventRequest();
        request.setEventType(EventType.EMAIL);
        EmailPayload payload = new EmailPayload();
        payload.setRecipient("test@example.com");
        payload.setMessage("Test message");
        request.setPayload(payload);
        request.setCallbackUrl("http://localhost/callback");

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").exists())
                .andExpect(jsonPath("$.message").value("Event accepted for processing."));
    }

    @Test
    public void testInvalidEventType() throws Exception {
        EventRequest request = new EventRequest();
        request.setEventType(null);
        request.setPayload(new EmailPayload());
        request.setCallbackUrl("http://localhost/callback");

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testMissingPayloadFields() throws Exception {
        EventRequest request = new EventRequest();
        request.setEventType(EventType.EMAIL);
        EmailPayload payload = new EmailPayload();
        request.setPayload(payload); // Missing recipient and message
        request.setCallbackUrl("http://localhost/callback");

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
} 