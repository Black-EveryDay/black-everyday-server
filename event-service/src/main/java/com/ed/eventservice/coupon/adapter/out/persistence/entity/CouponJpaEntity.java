package com.ed.eventservice.coupon.adapter.out.persistence.entity;

import com.ed.eventservice.coupon.domain.enums.CouponState;
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
import java.util.UUID;
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
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true, updatable = false, nullable = false, length = 36)
  private String publicId;

  @Column(nullable = false, length = 36)
  private String couponTemplateId;

  @Column(length = 36)
  private String userId;

  @Enumerated(EnumType.STRING)
  private CouponState state;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime expirationDate;

  @Column
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
    this.publicId = publicId == null ? UUID.randomUUID().toString() : publicId;
    this.couponTemplateId = couponTemplateId;
    this.userId = userId;
    this.state = state;
    this.expirationDate = expirationDate;
    this.issuedAt = LocalDateTime.now();
  }
}
