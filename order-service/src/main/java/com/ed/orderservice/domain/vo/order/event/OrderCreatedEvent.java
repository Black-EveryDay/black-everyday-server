package com.ed.orderservice.domain.vo.order.event;

import com.ed.orderservice.application.service.domain.events.OrderEventDto;
import com.ed.orderservice.domain.vo.order.OrderEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreatedEvent extends BaseOrderEvent{
  private final OrderEventDto orderEventDto;
  private final OrderEvent orderEvent;

  @Builder

  public OrderCreatedEvent(OrderEventDto orderEventDto, OrderEvent orderEvent) {
    this.orderEventDto = orderEventDto;
    this.orderEvent = orderEvent;
  }
}
