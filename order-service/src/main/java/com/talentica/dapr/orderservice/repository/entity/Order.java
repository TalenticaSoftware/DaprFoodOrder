package com.talentica.dapr.orderservice.repository.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Setter
@Getter
public class Order {

  private String orderId;
  private String restaurantId;
  private List<OrderLineItem> items;

  public Order(){
    this.orderId = UUID.randomUUID().toString();
  }









}
