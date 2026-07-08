package com.springeventspractice.event;

import com.springeventspractice.order.Order;

// POJO-style event (no need to extend ApplicationEvent)
public class OrderCreatedEvent {
    private final Order order;

    public OrderCreatedEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}