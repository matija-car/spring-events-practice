package com.springeventspractice.order;

import com.springeventspractice.event.OrderCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private final ApplicationEventPublisher eventPublisher;

    public OrderService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public Order createOrder(String customerEmail, double amount) {
        Order order = new Order(UUID.randomUUID().toString(), customerEmail, amount);
        eventPublisher.publishEvent(new OrderCreatedEvent(order));
        return order;
    }
}