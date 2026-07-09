package com.springeventspractice.listener;

import com.springeventspractice.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsListener {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsListener.class);

    @Order(3)
    @Async
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Recording analytics event for order {} with amount {} on thread {}",
                event.getOrder().getId(), event.getOrder().getAmount(), Thread.currentThread().getName());

        simulateSlowExternalCall();
    }

    private void simulateSlowExternalCall() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("Analytics call finished on thread {}", Thread.currentThread().getName());
    }
}