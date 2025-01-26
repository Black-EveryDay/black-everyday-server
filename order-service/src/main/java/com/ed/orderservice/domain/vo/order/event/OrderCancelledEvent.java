package com.ed.orderservice.domain.vo.order.event;

import com.ed.orderservice.application.service.domain.events.OrderEventDto;
import com.ed.orderservice.domain.vo.order.OrderEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCancelledEvent extends BaseOrderEvent  {
  private final OrderEventDto orderEventDto;
  private final OrderEvent orderEvent;

  @Builder
  private OrderCancelledEvent(OrderEvent orderEvent, OrderEventDto orderEventDto) {
    this.orderEvent = orderEvent;
    this.orderEventDto = orderEventDto;
  }

}
