package com.talentica.dapr.restaurantservice.service.external;

import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value ="RatingClient" , url = "${rating.url}")
public interface RatingClient {

    @GetMapping(value = "/v1/rating/restaurant/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    RatingDto get(@PathVariable(value = "id") String id);

    @Data
    public class RatingDto {
        private int rating;
    }

}
