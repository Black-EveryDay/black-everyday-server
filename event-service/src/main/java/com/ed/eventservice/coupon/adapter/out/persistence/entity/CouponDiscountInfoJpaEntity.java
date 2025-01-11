package com.ed.eventservice.coupon.adapter.out.persistence.entity;

import com.ed.eventservice.coupon.domain.enums.DiscountType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponDiscountInfoJpaEntity {

  @Enumerated(EnumType.STRING)
  private DiscountType discountType;

  @Column(precision = 10, scale = 2)
  private BigDecimal discountValue;

  @Builder
  private CouponDiscountInfoJpaEntity(DiscountType discountType, BigDecimal discountValue) {
    
    this.discountType = discountType;
    this.discountValue = discountValue;
  }
}
