package com.ed.orderservice.domain.vo.order;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreatedEvent {
  private final Order order;
  private final OrderEvent orderEvent;

  @Builder
  public OrderCreatedEvent(Order order, OrderEvent orderEvent) {
    this.order = order;
    this.orderEvent = orderEvent;
  }
}
