package com.ed.couponservice.coupon.application.port.out.dto;

import com.ed.couponservice.coupon.domain.enums.CouponState;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueCouponResponse {

  private UUID couponId;
  private LocalDateTime expirationDate;
  private LocalDateTime issuedAt;
  private UUID userId;
  private CouponState state;
  private CouponTemplateResponse couponTemplate;

  @Getter
  @Builder
  public static class CouponTemplateResponse {

    private UUID templateId;
    private String couponName;
  }
}
