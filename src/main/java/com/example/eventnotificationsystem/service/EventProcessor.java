package com.example.eventnotificationsystem.service;

import com.example.eventnotificationsystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class EventProcessor {
    @Autowired
    private EventService eventService;
    @Autowired
    private CallbackService callbackService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);
    private volatile boolean running = true;

    @PostConstruct
    public void startProcessing() {
        executorService.submit(() -> processQueue(eventService.getEmailQueue(), 5000, EventType.EMAIL));
        executorService.submit(() -> processQueue(eventService.getSmsQueue(), 3000, EventType.SMS));
        executorService.submit(() -> processQueue(eventService.getPushQueue(), 2000, EventType.PUSH));
    }

    private void processQueue(BlockingQueue<? extends Event> queue, long delayMillis, EventType eventType) {
        Random random = new Random();
        while (running) {
            try {
                Event event = queue.take();
                Thread.sleep(delayMillis); // Simulate processing time
                boolean isFailure = random.nextDouble() < 0.1; // 10% failure rate
                CallbackRequest callbackRequest = new CallbackRequest(
                        event.getEventId(),
                        isFailure ? "FAILED" : "COMPLETED",
                        eventType.toString(),
                        isFailure ? "Simulated processing failure" : null,
                        ZonedDateTime.now().toString()
                );
                callbackService.sendCallback(event.getCallbackUrl(), callbackRequest);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        running = false;
        eventService.stopAcceptingEvents();
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}