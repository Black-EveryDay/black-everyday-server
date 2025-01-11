package com.ed.eventservice.coupon.domain.vo;

import com.ed.eventservice.coupon.domain.enums.CouponState;
import com.ed.eventservice.coupon.domain.enums.CouponStatusChangeReason;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CouponStatusChangeLog {

  private final Long id;
  private final CouponState beforeStatus;
  private final CouponState afterStatus;
  private final CouponStatusChangeReason reason;
  private final LocalDateTime changedAt;
  private final UUID orderId;
  private final Long couponId;
}
