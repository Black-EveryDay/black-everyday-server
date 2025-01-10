package com.ed.eventservice.coupon.adapter.out.persistence.entity;

import com.ed.eventservice.coupon.domain.enums.CouponState;
import com.ed.eventservice.coupon.domain.enums.CouponStatusChangeReason;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "coupon_status_change_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponStatusChangeLogJpaEntity extends BaseJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  private CouponState before;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  private CouponState after;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  private CouponStatusChangeReason reason;

  @Column(nullable = false, updatable = false)
  private LocalDateTime changedAt;

  @Column(updatable = false)
  private UUID orderId;
}
