package com.ed.orderservice.domain.vo.order.settlement;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderSettlement {
  private String orderPublicId;
  private List<OrderItemSettlement> orderItems;

  @Builder
  private OrderSettlement(String orderPublicId, List<OrderItemSettlement> orderItems) {
    this.orderPublicId = orderPublicId;
    this.orderItems = orderItems;
  }
}
