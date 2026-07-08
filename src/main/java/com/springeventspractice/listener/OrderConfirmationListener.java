package com.springeventspractice.listener;

import com.springeventspractice.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConfirmationListener {

    private static final Logger log = LoggerFactory.getLogger(OrderConfirmationListener.class);

    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Sending order confirmation for order {} to email {}",
                event.getOrder().getId(), event.getOrder().getCustomerEmail());
    }
}