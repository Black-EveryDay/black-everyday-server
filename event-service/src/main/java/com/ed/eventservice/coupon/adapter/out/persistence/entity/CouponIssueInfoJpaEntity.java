package com.ed.eventservice.coupon.adapter.out.persistence.entity;

import com.ed.eventservice.coupon.domain.enums.CouponIssuanceType;
import com.ed.eventservice.coupon.domain.enums.CouponIssuerType;
import com.ed.eventservice.coupon.domain.vo.CouponIssueInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponIssueInfoJpaEntity {

  @Enumerated(EnumType.STRING)
  private CouponIssuanceType couponIssuanceType;

  @Enumerated(EnumType.STRING)
  private CouponIssuerType couponIssuerType;

  @Column(length = 36)
  private String couponIssuerId;

  @Column(nullable = false)
  private Boolean isIssuable;

  @Column
  private Integer maxIssuance;

  @Builder
  private CouponIssueInfoJpaEntity(CouponIssuanceType couponIssuanceType,
      CouponIssuerType couponIssuerType,
      UUID couponIssuerId, Boolean isIssuable, Integer maxIssuance) {
    this.couponIssuanceType = couponIssuanceType;
    this.couponIssuerType = couponIssuerType;
    this.couponIssuerId = couponIssuerId == null ? null : couponIssuerId.toString();
    this.isIssuable = isIssuable != null && isIssuable;
    this.maxIssuance = maxIssuance;
  }

  public static CouponIssueInfoJpaEntity from(CouponIssueInfo couponIssueInfo) {
    return CouponIssueInfoJpaEntity.builder()
        .couponIssuanceType(couponIssueInfo.getCouponIssuanceType())
        .couponIssuerType(couponIssueInfo.getCouponIssuerType())
        .couponIssuerId(couponIssueInfo.getCouponIssuerId())
        .isIssuable(couponIssueInfo.getIsIssuable())
        .maxIssuance(couponIssueInfo.getMaxIssuance())
        .build();
  }

  public CouponIssueInfo toCouponIssueInfo() {
    return CouponIssueInfo.builder()
        .couponIssuanceType(couponIssuanceType)
        .couponIssuerType(couponIssuerType)
        .couponIssuerId(couponIssuerId == null ? null : UUID.fromString(couponIssuerId))
        .isIssuable(isIssuable)
        .maxIssuance(maxIssuance)
        .build();
  }
}
