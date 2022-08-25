package com.talentica.dapr.restaurantservice.controller.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionData<T> {
    private T data;
}