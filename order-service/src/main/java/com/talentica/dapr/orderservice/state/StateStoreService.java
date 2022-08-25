package com.talentica.dapr.orderservice.state;

import com.talentica.dapr.orderservice.service.external.LocalProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateStoreService {

    @Autowired
    LocalProxy localProxy;

    public void save(String key, Object value, int ttlInSec){
        localProxy.storeState(List.of(new StateStore(key, value)), ttlInSec);
    }

    public Object get(String key){
        StateStore store = localProxy.getStoreState(key);
        return store.getValue().get("value");
    }

    public void remove(String key){
        localProxy.removeStoreState(key);
    }

    public void save(String key, Object value){
        save(key, value, -1);
    }
}
