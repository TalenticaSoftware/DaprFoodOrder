package com.talentica.dapr.restaurantservice.service;

import com.talentica.dapr.restaurantservice.repository.RestaurantRepository;
import com.talentica.dapr.restaurantservice.repository.entity.Restaurant;
import com.talentica.dapr.restaurantservice.service.external.LocalProxy;
import com.talentica.dapr.restaurantservice.service.external.RatingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository repository;

    @Autowired
    RatingClient ratingClient;

    @Autowired
    LocalProxy localProxy;

    public String createRestaurant(Restaurant restaurant){
        return repository.create(restaurant);
    }
    public Restaurant getRestaurant(String restaurantId){
        RatingClient.RatingDto ratingDto = ratingClient.get(restaurantId);
        Restaurant restaurant = repository.findById(restaurantId);
        restaurant.setRating(ratingDto.getRating());
        return restaurant;
    }

    public void placeOrderAtRestaurant(String orderId){
        Map map = localProxy.getSecret("apiKey");
        System.out.println("API key to call exteral api is : " + map.get("apiKey"));
    }
}
