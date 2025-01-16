package com.ed.couponservice.coupon.application.port.out.dto;

import com.ed.couponservice.coupon.domain.enums.DiscountType;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UseCouponResponse {

  private UUID couponId;
  private CouponTemplateResponse couponTemplate;

  @Getter
  @Builder
  public static class CouponTemplateResponse {

    private UUID templateId;
    private String couponName;
    private DiscountType discountType;
    private BigDecimal discountValue;
  }
}
