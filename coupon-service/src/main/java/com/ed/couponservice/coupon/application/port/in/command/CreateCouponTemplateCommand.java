package com.ed.couponservice.coupon.application.port.in.command;

import com.ed.couponservice.coupon.domain.enums.CouponIssuanceType;
import com.ed.couponservice.coupon.domain.enums.CouponIssuerType;
import com.ed.couponservice.coupon.domain.enums.CouponUsageTargetType;
import com.ed.couponservice.coupon.domain.enums.DiscountType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCouponTemplateCommand {

  private String couponName;
  private CouponIssuanceType couponIssuanceType;
  private CouponIssuerType couponIssuerType;
  private UUID couponIssuerId;
  private Integer maxIssuance;
  private CouponUsageTargetType couponUsageTargetType;
  private UUID couponUsageTargetId;
  private DiscountType discountType;
  private BigDecimal discountValue;
  private Long expirationDays;
  private LocalDateTime expirationDate;
  private Boolean isIssuable;
}
