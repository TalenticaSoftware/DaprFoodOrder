package com.talentica.dapr.restaurantservice.controller.event;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderCreatedEvent {
    private String orderId;
    private String restaurantId;
}
