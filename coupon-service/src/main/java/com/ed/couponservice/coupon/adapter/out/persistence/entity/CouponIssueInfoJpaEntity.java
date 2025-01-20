package com.ed.couponservice.coupon.adapter.out.persistence.entity;

import com.ed.couponservice.coupon.domain.enums.CouponIssuanceType;
import com.ed.couponservice.coupon.domain.enums.CouponIssuerType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponIssueInfoJpaEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "COUPON_ISSUANCE_TYPE", nullable = false)
  private CouponIssuanceType couponIssuanceType;

  @Enumerated(EnumType.STRING)
  @Column(name = "COUPON_ISSUER_TYPE", nullable = false)
  private CouponIssuerType couponIssuerType;

  @Column(name = "COUPON_ISSUER_ID", length = 36)
  private String couponIssuerId;

  @Column(name = "IS_ISSUABLE", nullable = false)
  private boolean isIssuable;

  @Column(name = "MAX_ISSUANCE")
  private Integer maxIssuance;

  @Builder
  private CouponIssueInfoJpaEntity(
      CouponIssuanceType couponIssuanceType,
      CouponIssuerType couponIssuerType,
      String couponIssuerId,
      boolean isIssuable,
      Integer maxIssuance
  ) {

    this.couponIssuanceType = couponIssuanceType;
    this.couponIssuerType = couponIssuerType;
    this.couponIssuerId = couponIssuerId;
    this.isIssuable = isIssuable;
    this.maxIssuance = maxIssuance;
  }
}
