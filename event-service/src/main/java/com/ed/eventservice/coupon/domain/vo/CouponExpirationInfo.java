package com.ed.eventservice.coupon.domain.vo;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponExpirationInfo {

  private final Duration expirationDays;
  private final LocalDateTime fixedExpirationDate;

  @Builder
  private CouponExpirationInfo(Duration expirationDays, LocalDateTime fixedExpirationDate) {
    this.expirationDays = expirationDays;
    this.fixedExpirationDate = fixedExpirationDate;
  }
}
