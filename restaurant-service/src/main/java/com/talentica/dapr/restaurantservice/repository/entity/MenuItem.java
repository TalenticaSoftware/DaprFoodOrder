package com.talentica.dapr.restaurantservice.repository.entity;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Setter
@Getter
public class MenuItem {
  private String menuItemId;
  private String name;
  private FoodType foodType;
  private BigDecimal price;
  public enum FoodType {
    VEG, NON_VEG, VEGAN;
  }

  public MenuItem(){
    this.menuItemId = UUID.randomUUID().toString();
  }

}
