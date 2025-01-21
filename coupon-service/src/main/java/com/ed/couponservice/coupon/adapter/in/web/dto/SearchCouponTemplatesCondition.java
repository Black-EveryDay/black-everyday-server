package com.ed.couponservice.coupon.adapter.in.web.dto;

import com.ed.couponservice.coupon.domain.enums.CouponIssuanceType;
import com.ed.couponservice.coupon.domain.enums.CouponIssuerType;
import com.ed.couponservice.coupon.domain.enums.CouponUsageTargetType;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchCouponTemplatesCondition {

  private UUID publicId;
  private String couponName;
  private CouponIssuanceType couponIssuanceType;
  private CouponIssuerType couponIssuerType;
  private UUID couponIssuerId;
  private Boolean isIssuable;
  private CouponUsageTargetType couponUsageTargetType;
  private UUID couponUsageTargetId;
}
