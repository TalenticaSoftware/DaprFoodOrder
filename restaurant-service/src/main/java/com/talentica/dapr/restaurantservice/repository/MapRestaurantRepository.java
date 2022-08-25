package com.talentica.dapr.restaurantservice.repository;


import com.talentica.dapr.restaurantservice.repository.entity.MenuItem;
import com.talentica.dapr.restaurantservice.repository.entity.Restaurant;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MapRestaurantRepository implements RestaurantRepository {
    Map<String, Restaurant> store = new HashMap<>();

    @PostConstruct
    private void init(){
        Restaurant r = new Restaurant();
        r.setRestaurantId("1");
        MenuItem menuItem = new MenuItem();
        menuItem.setMenuItemId("1");
        menuItem.setFoodType(MenuItem.FoodType.VEG);
        menuItem.setName("Dal Fry");
        menuItem.setPrice(new BigDecimal(150));
        r.setMenuItems(List.of(menuItem));
        r.setName("Kareems");
        this.store.put(r.getRestaurantId(), r);
    }

    @Override
    public String create(Restaurant restaurant) {
        store.put(restaurant.getRestaurantId(), restaurant);
        return restaurant.getRestaurantId();
    }

    @Override
    public Restaurant findById(String id) {
        return store.get(id);
    }
}
