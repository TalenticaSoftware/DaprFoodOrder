package com.talentica.dapr.orderservice.service.external;

import com.talentica.dapr.orderservice.event.DaprDomainEventPublisher;
import com.talentica.dapr.orderservice.state.StateStore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value ="LocalProxy" , url = "http://localhost:3500")
public interface LocalProxy {

    @PostMapping(value = "/v1.0/publish/pubsub/{topicName}", consumes = DaprDomainEventPublisher.CloudEvent.CONTENT_TYPE)
    void create(@PathVariable String topicName, @RequestBody DaprDomainEventPublisher.CloudEvent restaurant);

    @PostMapping(value = "/v1.0/state/statestore")
    void storeState(@RequestBody List<StateStore> state, @RequestParam(value = "metadata.ttlInSeconds") int ttlInSec);

    @GetMapping(value = "/v1.0/state/statestore/{key}")
    StateStore getStoreState(@PathVariable String key);

    @DeleteMapping(value = "/v1.0/state/statestore/{key}")
    void removeStoreState(@PathVariable String key);


}
