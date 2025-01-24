package com.ed.orderservice.presentaion.web.response.domain.cancel;

import com.ed.orderservice.domain.vo.order.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCancelResponse {
  private String orderId;
  private String cancelAmount;

  @Builder
  public OrderCancelResponse(String orderId, String cancelAmount) {
    this.orderId = orderId;
    this.cancelAmount = cancelAmount;
  }

  public static OrderCancelResponse from(Order order) {
    return OrderCancelResponse.builder()
        .orderId(order.getOrderPublicId())
        .cancelAmount(order.getTotalAmount().toString())
        .build();
  }
}
