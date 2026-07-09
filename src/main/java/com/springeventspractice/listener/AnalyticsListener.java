package com.springeventspractice.listener;

import com.springeventspractice.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsListener {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsListener.class);

    @Order(3)
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Recording analytics event for order {} with amount {}",
                event.getOrder().getId(), event.getOrder().getAmount());
    }
}