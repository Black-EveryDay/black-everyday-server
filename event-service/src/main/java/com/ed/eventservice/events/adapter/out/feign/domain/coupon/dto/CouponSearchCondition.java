package com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto;

import com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto.enums.CouponIssuanceType;
import com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto.enums.CouponIssuerType;
import com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto.enums.CouponUsageTargetType;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponSearchCondition {

  private UUID publicId;
  private String couponName;
  private CouponIssuanceType couponIssuanceType;
  private CouponIssuerType couponIssuerType;
  private UUID couponIssuerId;
  private Boolean isIssuable;
  private CouponUsageTargetType couponUsageTargetType;
  private UUID couponUsageTargetId;
}
