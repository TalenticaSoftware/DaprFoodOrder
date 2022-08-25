package com.talentica.dapr.orderservice.service.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value ="RestaurantClient" , url = "${restaurant.url}")
public interface RestaurantClient {

    @GetMapping(value = "/v1/restaurant/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    Restaurant get(@PathVariable(value = "id") String id);



    @Setter
    @Getter
    class Restaurant {
        private String name;
        @JsonProperty("restaurantId")
        private String id;
    }
}


