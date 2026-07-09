package com.springeventspractice.order;

import com.springeventspractice.event.OrderCreatedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RecordApplicationEvents
class OrderServiceEventTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ApplicationEvents applicationEvents;

    @Test
    void createOrder_publishesOrderCreatedEvent() {
        // when
        Order order = orderService.createOrder("test@test.com", 250.0);

        // then
        long eventCount = applicationEvents.stream(OrderCreatedEvent.class).count();
        assertThat(eventCount).isEqualTo(1);

        OrderCreatedEvent publishedEvent = applicationEvents.stream(OrderCreatedEvent.class)
                .findFirst()
                .orElseThrow();

        assertThat(publishedEvent.getOrder().getId()).isEqualTo(order.getId());
        assertThat(publishedEvent.getOrder().getCustomerEmail()).isEqualTo("test@test.com");
        assertThat(publishedEvent.getOrder().getAmount()).isEqualTo(250.0);
    }
}