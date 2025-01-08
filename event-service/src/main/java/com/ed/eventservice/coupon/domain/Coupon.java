package com.ed.eventservice.coupon.domain;

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
  private LocalDateTime expirationDate;
  private LocalDateTime issuedAt;
}
