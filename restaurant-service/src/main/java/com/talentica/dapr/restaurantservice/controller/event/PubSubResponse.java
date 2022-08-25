package com.talentica.dapr.restaurantservice.controller.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PubSubResponse {
    private String status;

    public static PubSubResponse retry(){
        return new PubSubResponse("RETRY");
    }
    public static PubSubResponse success(){
        return new PubSubResponse("SUCCESS");
    }
    public static PubSubResponse drop(){
        return new PubSubResponse("DROP");
    }
}
