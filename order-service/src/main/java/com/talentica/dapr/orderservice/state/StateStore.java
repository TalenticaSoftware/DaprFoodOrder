package com.talentica.dapr.orderservice.state;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class StateStore {
    private Map<String, Object> value;
    private String key;

    public StateStore(String key, Object value){
        this.key = key;
        this.value = Map.of("value", value);
    }
}
