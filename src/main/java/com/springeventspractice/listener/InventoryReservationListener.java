package com.springeventspractice.listener;

import com.springeventspractice.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class InventoryReservationListener {

    private static final Logger log = LoggerFactory.getLogger(InventoryReservationListener.class);

    @Order(1)
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Reserving inventory for order {}", event.getOrder().getId());
    }
}