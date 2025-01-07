package com.ed.eventservice.coupon.application.port.in;

import com.ed.eventservice.coupon.domain.CouponTemplate;
import com.ed.eventservice.coupon.domain.enums.CouponIssuanceType;
import com.ed.eventservice.coupon.domain.enums.CouponIssuerType;
import com.ed.eventservice.coupon.domain.enums.CouponUsageTargetType;
import com.ed.eventservice.coupon.domain.enums.DiscountType;
import com.ed.eventservice.coupon.domain.vo.CouponDiscountInfo;
import com.ed.eventservice.coupon.domain.vo.CouponExpirationInfo;
import com.ed.eventservice.coupon.domain.vo.CouponIssueInfo;
import com.ed.eventservice.coupon.domain.vo.CouponUsageTargetInfo;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public class CreateCouponTemplateCommand {

  private String couponName;
  private CouponIssuanceType couponIssuanceType;
  private CouponIssuerType couponIssuerType;
  private UUID couponIssuerId;
  private Integer maxIssuance;
  private CouponUsageTargetType couponUsageTargetType;
  private UUID couponUsageTargetId;
  private DiscountType discountType;
  private BigDecimal discountValue;
  private Long expirationDays;
  private LocalDateTime expirationDate;

  public CouponTemplate toDomain() {
    return CouponTemplate.builder()
        .couponName(this.couponName)
        .couponIssueInfo(this.toCouponIssueInfo())
        .couponUsageTargetInfo(this.toCouponUsageTargetInfo())
        .couponDiscountInfo(this.toCouponDiscountInfo())
        .couponExpirationInfo(this.toCouponExpirationInfo())
        .build();
  }

  private CouponIssueInfo toCouponIssueInfo() {
    return CouponIssueInfo.builder()
        .couponIssuanceType(this.couponIssuanceType)
        .couponIssuerType(this.couponIssuerType)
        .couponIssuerId(this.couponIssuerId)
        .maxIssuance(this.maxIssuance)
        .isIssuable(false)
        .build();
  }

  private CouponUsageTargetInfo toCouponUsageTargetInfo() {
    return CouponUsageTargetInfo.builder()
        .couponUsageTargetType(this.couponUsageTargetType)
        .couponUsageTargetId(this.couponUsageTargetId)
        .build();
  }

  private CouponDiscountInfo toCouponDiscountInfo() {
    return CouponDiscountInfo.builder()
        .discountType(this.discountType)
        .discountValue(this.discountValue)
        .build();
  }

  private CouponExpirationInfo toCouponExpirationInfo() {
    return CouponExpirationInfo.builder()
        .expirationDays(Duration.ofDays(this.expirationDays))
        .fixedExpirationDate(this.expirationDate)
        .build();
  }
}
