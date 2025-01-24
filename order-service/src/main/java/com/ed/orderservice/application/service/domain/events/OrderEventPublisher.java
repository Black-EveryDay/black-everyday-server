package com.ed.orderservice.application.service.domain.events;


import com.ed.orderservice.domain.enums.OrderEventType;
import com.ed.orderservice.domain.vo.order.OrderEvent;
import com.ed.orderservice.domain.vo.order.event.BaseOrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {
  private final ApplicationEventPublisher eventPublisher;
  private final OrderEventFactory orderEventFactory;

  public void publishOrderEvent(OrderEventDto orderEventDto, OrderEvent orderEvent, OrderEventType eventType) {
    BaseOrderEvent event = orderEventFactory.getEvent(orderEventDto, orderEvent, eventType);
    eventPublisher.publishEvent(event);
  }

}
