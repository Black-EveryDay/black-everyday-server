package com.ed.eventservice.coupon.adapter.in.internal.dto;

import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UseCouponRequest {

  private UUID userId;
  private UUID productId;
  private UUID brandId;
  @Size(min = 18, max = 18, message = "Invalid orderId")
  private String orderId;
}
