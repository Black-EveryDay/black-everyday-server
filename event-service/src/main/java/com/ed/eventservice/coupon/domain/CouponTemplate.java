package com.ed.eventservice.coupon.domain;

import com.ed.eventservice.coupon.domain.enums.CouponIssuerType;
import com.ed.eventservice.coupon.domain.enums.CouponUsageTargetType;
import com.ed.eventservice.coupon.domain.vo.CouponDiscountInfo;
import com.ed.eventservice.coupon.domain.vo.CouponExpirationInfo;
import com.ed.eventservice.coupon.domain.vo.CouponIssueInfo;
import com.ed.eventservice.coupon.domain.vo.CouponUsageTargetInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
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
  private final List<Coupon> coupons;

  @Builder
  private CouponTemplate(Long id, UUID publicId, String couponName, CouponIssueInfo couponIssueInfo,
      CouponUsageTargetInfo couponUsageTargetInfo, CouponDiscountInfo couponDiscountInfo,
      CouponExpirationInfo couponExpirationInfo, List<Coupon> coupons) {
    this.id = id;
    this.publicId = publicId;
    this.couponName = couponName;
    this.couponIssueInfo = couponIssueInfo;
    this.couponUsageTargetInfo = couponUsageTargetInfo;
    this.couponDiscountInfo = couponDiscountInfo;
    this.couponExpirationInfo = couponExpirationInfo;
    this.coupons = coupons == null ? List.of() : coupons;
    validityCheck();
  }

  private void validityCheck() {
    checkCouponIssuerTypeIsBrandAndIssuerIdIsNotNull();
    checkCouponUsageTargetTypeIsNotAllAndUsageTargetIdIsNotNull();
    checkMaxIssuanceIsNullOrGreaterThanZero();
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

  private void checkMaxIssuanceIsNullOrGreaterThanZero() {
    if (this.couponIssueInfo.getMaxIssuance() != null
        && this.couponIssueInfo.getMaxIssuance() <= 0) {
      throw new IllegalArgumentException("Max issuance must be greater than 0");
    }
  }

  public void createCoupon(Integer quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than 0");
    }
    if (this.couponIssueInfo.getMaxIssuance() != null
        && this.couponIssueInfo.getMaxIssuance()
        < coupons.size() + quantity) {
      throw new IllegalArgumentException("Quantity must be less than or equal to max issuance");
    }

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime expirationDate = this.couponExpirationInfo.getExpirationDays() == null ? null
        : now.plusDays(this.couponExpirationInfo.getExpirationDays().toDays());

    this.coupons.addAll(
        IntStream.range(0, quantity)
            .mapToObj(i -> Coupon.builder()
                .couponTemplateId(this.publicId)
                .issuedAt(now)
                .expirationDate(expirationDate)
                .build())
            .toList()
    );
  }
}
