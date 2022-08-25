package com.talentica.dapr.orderservice.service;

import com.talentica.dapr.orderservice.event.DomainEventPublisher;
import com.talentica.dapr.orderservice.event.OrderCancelled;
import com.talentica.dapr.orderservice.event.OrderCreated;
import com.talentica.dapr.orderservice.repository.OrderRepository;
import com.talentica.dapr.orderservice.repository.entity.Order;
import com.talentica.dapr.orderservice.service.external.RestaurantClient;
import com.talentica.dapr.orderservice.state.StateStore;
import com.talentica.dapr.orderservice.state.StateStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {
    @Autowired
    OrderRepository repository;
    @Autowired
    RestaurantClient restaurantClient;
    @Autowired
    DomainEventPublisher eventPublisher;
    @Autowired
    StateStoreService stateStoreService;
    public String createOrder(Order order){
        RestaurantClient.Restaurant restaurant = restaurantClient.get(order.getRestaurantId());
        String orderId = repository.create(order);
        eventPublisher.publish("ORDER", orderId, new OrderCreated(orderId, order.getRestaurantId()));
        stateStoreService.save("OrderId", orderId, 60);
        return orderId;
    }
    public Order getOrder(String orderId){
        return repository.findById(orderId);
    }
    public void cancelOrder(String orderId) {
        if(orderId.equals(stateStoreService.get("OrderId"))){
            log.info(orderId + ", Order cancelled successfully.");
            eventPublisher.publish("ORDER", orderId, new OrderCancelled(orderId));
            stateStoreService.remove("OrderId");
        }else {
            throw new RuntimeException("Order can not be cancelled now.");
        }
    }
}
