package com.ed.orderservice.infrastructure.external.fegin.domain.coupon.dto;

import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.enums.DiscountType;
import java.math.BigDecimal;
import java.util.UUID;

public record UseCouponResponse(
    UUID couponId,
    CouponTemplateResponse couponTemplate
) {

  public record CouponTemplateResponse(
      UUID templateId,
      String couponName,
      DiscountType discountType,
      BigDecimal discountValue
  ) {

  }
}
