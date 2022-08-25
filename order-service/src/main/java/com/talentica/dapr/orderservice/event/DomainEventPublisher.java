package com.talentica.dapr.orderservice.event;

import java.util.List;

public interface DomainEventPublisher {

  void publish(String aggregateType, Object aggregateId, DomainEvent domainEvent);

}
