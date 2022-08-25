package com.talentica.dapr.restaurantservice.repository.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Setter
@Getter
public class Restaurant {

  private String restaurantId;
  private List<MenuItem> menuItems;
  private int rating;

  private String name;

  public Restaurant() {
    restaurantId = UUID.randomUUID().toString();
  }

}
