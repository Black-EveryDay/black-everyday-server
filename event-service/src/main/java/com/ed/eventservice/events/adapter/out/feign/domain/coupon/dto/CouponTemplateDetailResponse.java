package com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto;

import com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto.enums.CouponIssuanceType;
import com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto.enums.CouponIssuerType;
import com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto.enums.CouponUsageTargetType;
import com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto.enums.DiscountType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponTemplateDetailResponse {

  private Long id;
  private UUID publicId;
  private String couponName;
  private CouponIssuanceType couponIssuanceType;
  private CouponIssuerType couponIssuerType;
  private UUID couponIssuerId;
  private Integer maxIssuance;
  private Boolean isIssuable;
  private CouponUsageTargetType couponUsageTargetType;
  private UUID couponUsageTargetId;
  private DiscountType discountType;
  private BigDecimal discountValue;
  private Long expirationDays;
  private LocalDateTime expirationDate;
}
