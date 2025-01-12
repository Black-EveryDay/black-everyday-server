package com.ed.orderservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemCouponDto {
  private final String orderItemCouponId;

  @Builder
  public OrderItemCouponDto(String orderItemCouponId) {
    this.orderItemCouponId = orderItemCouponId;
  }
}
