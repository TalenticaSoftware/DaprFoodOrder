package com.talentica.dapr.orderservice.repository;

import com.talentica.dapr.orderservice.repository.entity.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MapOrderRepository implements OrderRepository {
    Map<String, Order> store = new HashMap<>();

    @Override
    public String create(Order order) {
        store.put(order.getOrderId(), order);
        return order.getOrderId();
    }

    @Override
    public Order findById(String id) {
        return store.get(id);
    }
}
