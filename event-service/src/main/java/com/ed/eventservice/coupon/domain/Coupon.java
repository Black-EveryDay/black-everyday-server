package com.ed.eventservice.coupon.domain;

import com.ed.eventservice.coupon.domain.enums.CouponState;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Coupon {

  private Long id;
  private UUID publicId;
  private UUID couponTemplateId;
  private UUID userId;
  private CouponState state;
  private LocalDateTime expirationDate;
  private LocalDateTime issuedAt;
}
