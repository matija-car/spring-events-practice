package com.springeventspractice.listener;

import com.springeventspractice.event.OrderCreatedEvent;
import com.springeventspractice.order.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;

class InventoryReservationListenerTest {

    private final InventoryReservationListener listener = new InventoryReservationListener();

    @Test
    void handleOrderCreated_throwsException_whenAmountExceedsLimit() {
        Order order = new Order("order-1", "test@test.com", 1500.0);
        OrderCreatedEvent event = new OrderCreatedEvent(order);

        assertThatThrownBy(() -> listener.handleOrderCreated(event))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Insufficient inventory");
    }

    @Test
    void handleOrderCreated_doesNotThrow_whenAmountWithinLimit() {
        Order order = new Order("order-2", "test@test.com", 500.0);
        OrderCreatedEvent event = new OrderCreatedEvent(order);

        assertThatCode(() -> listener.handleOrderCreated(event))
                .doesNotThrowAnyException();
    }
}