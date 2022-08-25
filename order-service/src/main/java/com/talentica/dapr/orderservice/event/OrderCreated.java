package com.talentica.dapr.orderservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OrderCreated implements DomainEvent {
    private String orderId;
    private String restaurantId;
}
