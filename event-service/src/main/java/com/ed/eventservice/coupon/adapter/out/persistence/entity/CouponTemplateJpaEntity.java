package com.ed.eventservice.coupon.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ed_coupon_templates")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponTemplateJpaEntity extends BaseJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false, length = 36)
  private String publicId;

  @Column(nullable = false)
  private String couponName;

  @Embedded
  private CouponIssueInfoJpaEntity couponIssueInfoJpaEntity;

  @Embedded
  private CouponUsageTargetInfoJpaEntity couponUsageTargetInfoJpaEntity;

  @Embedded
  private CouponDiscountInfoJpaEntity couponDiscountInfoJpaEntity;

  @Embedded
  private CouponExpirationInfoJpaEntity couponExpirationInfoJpaEntity;

  @Builder
  private CouponTemplateJpaEntity(Long id, String publicId, String couponName,
      CouponIssueInfoJpaEntity couponIssueInfoJpaEntity,
      CouponUsageTargetInfoJpaEntity couponUsageTargetInfoJpaEntity,
      CouponDiscountInfoJpaEntity couponDiscountInfoJpaEntity,
      CouponExpirationInfoJpaEntity couponExpirationInfoJpaEntity) {
    this.id = id;
    this.publicId = publicId;
    this.couponName = couponName;
    this.couponIssueInfoJpaEntity = couponIssueInfoJpaEntity;
    this.couponUsageTargetInfoJpaEntity = couponUsageTargetInfoJpaEntity;
    this.couponDiscountInfoJpaEntity = couponDiscountInfoJpaEntity;
    this.couponExpirationInfoJpaEntity = couponExpirationInfoJpaEntity;
  }
}
