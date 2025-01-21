package com.ed.couponservice.coupon.domain.vo;

import com.ed.couponservice.coupon.domain.enums.CouponState;
import com.ed.couponservice.coupon.domain.enums.CouponStatusChangeReason;
import java.time.LocalDateTime;
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
  private final String orderId;
  private final Long couponId;
}
