package com.ed.orderservice.domain.vo.order.settlement;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponSettlement {
  private String couponTemplatePublicId;
  private String orderCouponPublicId;
  private BigDecimal discountAmount;

  @Builder
  private CouponSettlement(String couponTemplatePublicId, String orderCouponPublicId,
      BigDecimal discountAmount) {
    this.couponTemplatePublicId = couponTemplatePublicId;
    this.orderCouponPublicId = orderCouponPublicId;
    this.discountAmount = discountAmount;
  }
}
