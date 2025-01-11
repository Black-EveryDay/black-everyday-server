package com.ed.eventservice.coupon.adapter.in.internal.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UseCouponRequest {

  private UUID userId;
  private UUID productId;
  private UUID brandId;
  private UUID orderId;
}
