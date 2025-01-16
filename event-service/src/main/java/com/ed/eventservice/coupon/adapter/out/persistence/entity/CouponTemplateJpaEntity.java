package com.ed.eventservice.coupon.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ED_COUPON_TEMPLATE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponTemplateJpaEntity extends BaseJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "COUPON_TEMPLATE_ID")
  private Long id;

  @Column(name = "COUPON_TEMPLATE_PUBLIC_ID", unique = true, updatable = false, nullable = false, length = 36)
  private String publicId = UUID.randomUUID().toString();

  @Column(name = "COUPON_NAME", nullable = false)
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
  private CouponTemplateJpaEntity(Long id,
      String couponName,
      CouponIssueInfoJpaEntity couponIssueInfoJpaEntity,
      CouponUsageTargetInfoJpaEntity couponUsageTargetInfoJpaEntity,
      CouponDiscountInfoJpaEntity couponDiscountInfoJpaEntity,
      CouponExpirationInfoJpaEntity couponExpirationInfoJpaEntity
  ) {

    this.id = id;
    this.couponName = couponName;
    this.couponIssueInfoJpaEntity = couponIssueInfoJpaEntity;
    this.couponUsageTargetInfoJpaEntity = couponUsageTargetInfoJpaEntity;
    this.couponDiscountInfoJpaEntity = couponDiscountInfoJpaEntity;
    this.couponExpirationInfoJpaEntity = couponExpirationInfoJpaEntity;
  }
}
