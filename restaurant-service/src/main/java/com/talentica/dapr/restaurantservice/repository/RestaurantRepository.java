package com.talentica.dapr.restaurantservice.repository;

import com.talentica.dapr.restaurantservice.repository.entity.Restaurant;

public interface RestaurantRepository {

    String create(Restaurant order);

    Restaurant findById(String id);
}
