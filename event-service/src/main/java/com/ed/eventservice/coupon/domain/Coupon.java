package com.ed.eventservice.coupon.domain;

import com.ed.eventservice.coupon.domain.enums.CouponState;
import com.ed.eventservice.coupon.domain.vo.CouponStatusChangeLog;
import java.time.LocalDateTime;
import java.util.List;
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
  private List<CouponStatusChangeLog> couponStatusChangelogs;

}
