package com.ed.eventservice.coupon.domain;

import com.ed.eventservice.coupon.domain.enums.CouponIssuerType;
import com.ed.eventservice.coupon.domain.enums.CouponUsageTargetType;
import com.ed.eventservice.coupon.domain.vo.CouponDiscountInfo;
import com.ed.eventservice.coupon.domain.vo.CouponExpirationInfo;
import com.ed.eventservice.coupon.domain.vo.CouponIssueInfo;
import com.ed.eventservice.coupon.domain.vo.CouponUsageTargetInfo;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponTemplate {

  private final Long id;
  private final UUID publicId;
  private final String couponName;
  private final CouponIssueInfo couponIssueInfo;
  private final CouponUsageTargetInfo couponUsageTargetInfo;
  private final CouponDiscountInfo couponDiscountInfo;
  private final CouponExpirationInfo couponExpirationInfo;

  @Builder
  private CouponTemplate(Long id, UUID publicId, String couponName, CouponIssueInfo couponIssueInfo,
      CouponUsageTargetInfo couponUsageTargetInfo, CouponDiscountInfo couponDiscountInfo,
      CouponExpirationInfo couponExpirationInfo) {
    this.id = id;
    this.publicId = publicId;
    this.couponName = couponName;
    this.couponIssueInfo = couponIssueInfo;
    this.couponUsageTargetInfo = couponUsageTargetInfo;
    this.couponDiscountInfo = couponDiscountInfo;
    this.couponExpirationInfo = couponExpirationInfo;

    validityCheck();
  }

  private void validityCheck() {
    checkCouponIssuerTypeIsBrandAndIssuerIdIsNotNull();
    checkCouponUsageTargetTypeIsNotAllAndUsageTargetIdIsNotNull();
  }

  private void checkCouponIssuerTypeIsBrandAndIssuerIdIsNotNull() {
    if (this.couponIssueInfo.getCouponIssuerType().equals(CouponIssuerType.BRAND)
        && this.couponIssueInfo.getCouponIssuerId() == null) {
      throw new IllegalArgumentException(
          "Coupon issuer id must not be null when coupon issuer type is brand");
    }
  }

  private void checkCouponUsageTargetTypeIsNotAllAndUsageTargetIdIsNotNull() {
    if (!this.getCouponUsageTargetInfo().getCouponUsageTargetType()
        .equals(CouponUsageTargetType.ALL)
        && this.getCouponUsageTargetInfo().getCouponUsageTargetId() == null) {
      throw new IllegalArgumentException(
          "Coupon usage target id must not be null when coupon usage target type is not all");
    }
  }
}
