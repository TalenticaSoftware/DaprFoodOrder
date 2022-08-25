package com.talentica.dapr.orderservice.repository.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class OrderLineItem {

  public OrderLineItem() {
  }

  private int quantity;
  private String menuItemId;
  private String name;

  private BigDecimal price;


  public OrderLineItem(String menuItemId, String name, BigDecimal price, int quantity) {
    this.menuItemId = menuItemId;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

  public BigDecimal getTotal() {
    return price.multiply(new BigDecimal(quantity));
  }

}
