package com.talentica.dapr.orderservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OrderCancelled implements DomainEvent {
    private String orderId;
}
