package com.talentica.dapr.restaurantservice.controller;


import com.talentica.dapr.restaurantservice.repository.entity.Restaurant;
import com.talentica.dapr.restaurantservice.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/restaurant")
@Slf4j
public class RestaurantController {

  @Autowired
  RestaurantService service;

  @PostMapping
  String createRestaurant(@RequestBody Restaurant restaurant){
    return service.createRestaurant(restaurant);
  }

  @GetMapping("/{restaurantId}")
  Restaurant getOrder(@PathVariable("restaurantId") String restaurantId){
    return service.getRestaurant(restaurantId);
  }

}
