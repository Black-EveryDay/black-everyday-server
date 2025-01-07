package com.ed.eventservice.coupon.adapter.out.persistence.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponExpirationInfoJpaEntity {

  @Temporal(TemporalType.TIMESTAMP)
  private Duration expirationDays;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime fixedExpirationDate;

  @Builder
  private CouponExpirationInfoJpaEntity(Duration expirationDays,
      LocalDateTime fixedExpirationDate) {
    this.expirationDays = expirationDays;
    this.fixedExpirationDate = fixedExpirationDate;
  }
}
