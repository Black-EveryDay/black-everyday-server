package com.ed.orderservice.infrastructure.external.fegin.domain.coupon.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UseCouponRequest {
  private String orderId;
  private UUID userId;
  private UUID brandId;
  private UUID productId;

  @Builder
  private UseCouponRequest(String orderId, UUID userId, UUID brandId, UUID productId) {
    this.orderId = orderId;
    this.userId = userId;
    this.brandId = brandId;
    this.productId = productId;
  }
}

