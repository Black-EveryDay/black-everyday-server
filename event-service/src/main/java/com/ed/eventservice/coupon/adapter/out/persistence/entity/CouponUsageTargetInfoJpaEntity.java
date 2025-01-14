package com.ed.eventservice.coupon.adapter.out.persistence.entity;

import com.ed.eventservice.coupon.domain.enums.CouponUsageTargetType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponUsageTargetInfoJpaEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "COUPON_USAGE_TARGET_TYPE")
  private CouponUsageTargetType couponUsageTargetType;

  @Column(name = "COUPON_USAGE_TARGET_ID", length = 36)
  private String couponUsageTargetId;

  @Builder
  private CouponUsageTargetInfoJpaEntity(
      CouponUsageTargetType couponUsageTargetType,
      UUID couponUsageTargetId
  ) {
    this.couponUsageTargetType = couponUsageTargetType;
    this.couponUsageTargetId = couponUsageTargetId == null ? null : couponUsageTargetId.toString();
  }
}
