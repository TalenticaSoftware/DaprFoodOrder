package com.talentica.dapr.restaurantservice.service.external;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value ="LocalProxy" , url = "http://localhost:3500")
public interface LocalProxy {

    @GetMapping(value = "/v1.0/secrets/secretstore/{secretName}")
    Map getSecret(@PathVariable String secretName);

}
