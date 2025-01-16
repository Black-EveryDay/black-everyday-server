package com.ed.couponservice.coupon.application.port.in;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCouponCommand {

  private final UUID couponTemplateId;
  private final Integer quantity;
}
