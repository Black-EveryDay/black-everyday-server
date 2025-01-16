package com.ed.couponservice.coupon.application.port.in;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponUseCommand {

  private final UUID couponId;
  private final UUID userId;
  private final UUID productId;
  private final UUID brandId;
  private final String orderId;
}
