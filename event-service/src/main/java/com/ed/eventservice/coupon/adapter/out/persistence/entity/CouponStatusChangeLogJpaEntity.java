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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ED_COUPON_STATUS_CHANGE_LOG")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponStatusChangeLogJpaEntity extends BaseJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  private CouponState beforeStatus;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  private CouponState afterStatus;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  private CouponStatusChangeReason reason;

  @Column(nullable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime changedAt;

  @Column(updatable = false, length = 18)
  private String orderId;

  @Column(nullable = false, updatable = false)
  private Long couponId;
}
