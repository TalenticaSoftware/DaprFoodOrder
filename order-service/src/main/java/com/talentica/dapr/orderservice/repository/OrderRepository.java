package com.talentica.dapr.orderservice.repository;


import com.talentica.dapr.orderservice.repository.entity.Order;

public interface OrderRepository  {

  String create(Order order);

  Order findById(String id);

}
