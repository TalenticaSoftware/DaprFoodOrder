package com.talentica.dapr.restaurantservice.controller.event;

import com.talentica.dapr.restaurantservice.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/e")
@Slf4j
public class EventListenerController {
    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/order-created")
    public PubSubResponse orderCreated(@RequestBody SubscriptionData<OrderCreatedEvent> data){
        System.out.println("Got order created events : " + data.getData().getOrderId());
        restaurantService.placeOrderAtRestaurant(data.getData().getOrderId());
        //throw new RuntimeException("Testing dlq"); //This will trigger dead letter queue if enabled, else just drop the message after logging it.
        return PubSubResponse.success();
        //return PubSubResponse.drop(); // This will drop the message even if dead letter queue if enabled. This is usefull if application come to know
        //That there is no point in retry the message as the message is faulty.
        //return PubSubResponse.retry(); //This does not require dead letter queue to be enabled. But it respects "backOffMaxRetries"
    }
}
