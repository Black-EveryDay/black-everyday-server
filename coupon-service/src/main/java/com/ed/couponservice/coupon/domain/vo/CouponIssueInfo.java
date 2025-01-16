package com.ed.couponservice.coupon.domain.vo;

import com.ed.couponservice.coupon.domain.enums.CouponIssuanceType;
import com.ed.couponservice.coupon.domain.enums.CouponIssuerType;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponIssueInfo {

  private final CouponIssuanceType couponIssuanceType;
  private final CouponIssuerType couponIssuerType;
  private final UUID couponIssuerId;
  private final Boolean isIssuable;
  private final Integer maxIssuance;
  private final Integer issuedCount;

  @Builder
  private CouponIssueInfo(CouponIssuanceType couponIssuanceType, CouponIssuerType couponIssuerType,
      UUID couponIssuerId, Boolean isIssuable, Integer maxIssuance, Integer issuedCount) {
    this.couponIssuanceType = couponIssuanceType;
    this.couponIssuerType = couponIssuerType;
    this.couponIssuerId = couponIssuerId;
    this.isIssuable = isIssuable;
    this.maxIssuance = maxIssuance;
    this.issuedCount = issuedCount;
  }
}
