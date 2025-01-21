package com.ed.couponservice.coupon.adapter.out.persistence.entity;

import com.ed.couponservice.coupon.domain.enums.CouponState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ED_COUPON")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponJpaEntity extends BaseJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "COUPON_ID")
  private Long id;

  @Column(name = "COUPON_PUBLIC_ID", unique = true, updatable = false, nullable = false, length = 36)
  private String publicId;

  @Column(name = "COUPON_TEMPLATE_ID", nullable = false, length = 36)
  private String couponTemplateId;

  @Column(name = "USER_ID", length = 36)
  private String userId;

  @Enumerated(EnumType.STRING)
  @Column(name = "COUPON_STATE", nullable = false)
  private CouponState state;

  @Column(name = "EXPIRATION_DATE")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime expirationDate;

  @Column(name = "ISSUED_AT")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime issuedAt;

  @Builder
  private CouponJpaEntity(
      Long id,
      String publicId,
      String couponTemplateId,
      String userId,
      CouponState state,
      LocalDateTime expirationDate
  ) {

    this.id = id;
    this.publicId = publicId;
    this.couponTemplateId = couponTemplateId;
    this.userId = userId;
    this.state = state;
    this.expirationDate = expirationDate;
    this.issuedAt = LocalDateTime.now();
  }
}
