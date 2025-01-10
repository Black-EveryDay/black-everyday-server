package com.ed.eventservice.coupon.domain.vo;

import com.ed.eventservice.coupon.domain.enums.CouponUsageTargetType;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponUsageTargetInfo {

  private final CouponUsageTargetType couponUsageTargetType;
  private final UUID couponUsageTargetId;

  @Builder
  private CouponUsageTargetInfo(
      CouponUsageTargetType couponUsageTargetType,
      UUID couponUsageTargetId
  ) {
    this.couponUsageTargetType = couponUsageTargetType;
    this.couponUsageTargetId = couponUsageTargetId;
  }
}
