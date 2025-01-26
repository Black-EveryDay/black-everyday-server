package com.ed.orderservice.domain.vo.order.settlement;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemSettlement {
  private String orderItemPublicId;
  private String brandPublicId;
  private String productPublicId;
  private int quantity;
  private Long unitPrice;
  private CouponSettlement coupon;


  @Builder
  private OrderItemSettlement(String orderItemPublicId, String brandPublicId, String productPublicId,
      int quantity, Long unitPrice, CouponSettlement coupon) {
    this.orderItemPublicId = orderItemPublicId;
    this.brandPublicId = brandPublicId;
    this.productPublicId = productPublicId;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.coupon = coupon;
  }
}
