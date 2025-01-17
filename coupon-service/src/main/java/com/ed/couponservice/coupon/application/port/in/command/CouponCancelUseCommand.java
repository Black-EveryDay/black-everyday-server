package com.ed.couponservice.coupon.application.port.in.command;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponCancelUseCommand {

  UUID couponId;
}
