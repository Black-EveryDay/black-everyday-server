package com.ed.couponservice.coupon.domain.vo;

import com.ed.couponservice.coupon.domain.enums.DiscountType;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponDiscountInfo {

  private final DiscountType discountType;
  private final BigDecimal discountValue;

  @Builder
  private CouponDiscountInfo(DiscountType discountType, BigDecimal discountValue) {
    this.discountType = discountType;
    this.discountValue = discountValue;
  }
}
