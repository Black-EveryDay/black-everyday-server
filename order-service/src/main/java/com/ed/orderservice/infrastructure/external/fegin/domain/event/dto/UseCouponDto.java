package com.ed.orderservice.infrastructure.external.fegin.domain.event.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UseCouponDto {

  private String orderId;
  private UUID userId;
  private UUID brandId;
  private UUID productId;
  private UUID couponPublicId;

  @Builder
  private UseCouponDto(String orderId, UUID userId, UUID brandId, UUID productId,
      UUID couponPublicId) {
    this.orderId = orderId;
    this.userId = userId;
    this.brandId = brandId;
    this.productId = productId;
    this.couponPublicId = couponPublicId;
  }

}
