package com.ed.orderservice.application.service.domain.events;

import com.ed.orderservice.domain.enums.OrderEventType;
import com.ed.orderservice.domain.vo.order.OrderEvent;
import com.ed.orderservice.domain.vo.order.event.BaseOrderEvent;
import com.ed.orderservice.domain.vo.order.event.OrderCancelledEvent;
import com.ed.orderservice.domain.vo.order.event.OrderCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class OrderEventFactory {
  public BaseOrderEvent getEvent(OrderEventDto orderEventDto, OrderEvent orderEvent, OrderEventType eventType) {
    return switch (eventType) {
      case CREATED -> OrderCreatedEvent.builder()
          .orderEventDto(orderEventDto)
          .orderEvent(orderEvent)
          .build();
      case CANCELLED -> OrderCancelledEvent.builder()
          .orderEventDto(orderEventDto)
          .orderEvent(orderEvent)
          .build();
    };
  }
}


