package com.ed.eventservice.coupon.application.port.in;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueCouponCommand {

  private UUID userId;
  private UUID couponId;
}
