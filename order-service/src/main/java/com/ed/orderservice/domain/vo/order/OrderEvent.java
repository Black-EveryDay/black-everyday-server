package com.ed.orderservice.domain.vo.order;

import com.ed.orderservice.domain.enums.OrderEventStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderEvent {

  private Long orderEventId;
  private String orderEventPublicId;
  private Order order;
  private OrderEventStatus orderEventStatus;
  private byte[] playLoad;

  @Builder
  public OrderEvent(Long orderEventId, String orderEventPublicId, Order order,
      OrderEventStatus orderEventStatus,
      byte[] playLoad) {
    this.orderEventId = orderEventId;
    this.orderEventPublicId = orderEventPublicId;
    this.order = order;
    this.orderEventStatus = orderEventStatus;
    this.playLoad = playLoad;
  }

}
