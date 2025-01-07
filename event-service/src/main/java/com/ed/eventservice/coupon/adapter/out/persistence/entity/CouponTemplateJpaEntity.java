package com.ed.eventservice.coupon.adapter.out.persistence.entity;

import com.ed.eventservice.coupon.domain.CouponTemplate;
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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ed_coupon_templates")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponTemplateJpaEntity extends BaseJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, nullable = false, length = 36)
  private String publicId = UUID.randomUUID().toString();

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
  private CouponTemplateJpaEntity(Long id, String couponName,
      CouponIssueInfoJpaEntity couponIssueInfoJpaEntity,
      CouponUsageTargetInfoJpaEntity couponUsageTargetInfoJpaEntity,
      CouponDiscountInfoJpaEntity couponDiscountInfoJpaEntity,
      CouponExpirationInfoJpaEntity couponExpirationInfoJpaEntity) {
    this.id = id;
    this.couponName = couponName;
    this.couponIssueInfoJpaEntity = couponIssueInfoJpaEntity;
    this.couponUsageTargetInfoJpaEntity = couponUsageTargetInfoJpaEntity;
    this.couponDiscountInfoJpaEntity = couponDiscountInfoJpaEntity;
    this.couponExpirationInfoJpaEntity = couponExpirationInfoJpaEntity;
  }

  public static CouponTemplateJpaEntity from(CouponTemplate couponTemplate) {
    return CouponTemplateJpaEntity.builder()
        .couponName(couponTemplate.getCouponName())
        .couponIssueInfoJpaEntity(
            CouponIssueInfoJpaEntity.from(couponTemplate.getCouponIssueInfo()))
        .couponUsageTargetInfoJpaEntity(
            CouponUsageTargetInfoJpaEntity.from(couponTemplate.getCouponUsageTargetInfo()))
        .couponDiscountInfoJpaEntity(
            CouponDiscountInfoJpaEntity.from(couponTemplate.getCouponDiscountInfo()))
        .couponExpirationInfoJpaEntity(
            CouponExpirationInfoJpaEntity.from(couponTemplate.getCouponExpirationInfo()))
        .build();
  }

  public CouponTemplate toDomain() {
    return CouponTemplate.builder()
        .id(this.id)
        .publicId(UUID.fromString(this.publicId))
        .couponName(this.couponName)
        .couponIssueInfo(this.couponIssueInfoJpaEntity.toCouponIssueInfo())
        .couponUsageTargetInfo(this.couponUsageTargetInfoJpaEntity.toCouponUsageTargetInfo())
        .couponDiscountInfo(this.couponDiscountInfoJpaEntity.toCouponDiscountInfo())
        .couponExpirationInfo(this.couponExpirationInfoJpaEntity.toCouponExpirationInfo())
        .build();
  }
}
