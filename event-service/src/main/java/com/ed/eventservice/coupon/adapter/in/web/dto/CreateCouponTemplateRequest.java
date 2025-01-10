package com.ed.eventservice.coupon.adapter.in.web.dto;

import com.ed.eventservice.coupon.application.port.in.CreateCouponTemplateCommand;
import com.ed.eventservice.coupon.domain.enums.CouponIssuanceType;
import com.ed.eventservice.coupon.domain.enums.CouponIssuerType;
import com.ed.eventservice.coupon.domain.enums.CouponUsageTargetType;
import com.ed.eventservice.coupon.domain.enums.DiscountType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCouponTemplateRequest {

  @NotNull
  private String couponName;
  @NotNull
  private CouponIssuanceType couponIssuanceType;
  @NotNull
  private CouponIssuerType couponIssuerType;
  private UUID couponIssuerId;
  @Min(1)
  private Integer maxIssuance;
  @NotNull
  private CouponUsageTargetType couponUsageTargetType;
  private UUID couponUsageTargetId;
  @NotNull
  private DiscountType discountType;
  @NotNull
  private BigDecimal discountValue;
  @Min(1)
  private Long expirationDays;
  private LocalDateTime expirationDate;

  public CreateCouponTemplateCommand toCommand() {
    return CreateCouponTemplateCommand.builder()
        .couponName(this.couponName)
        .couponIssuanceType(this.couponIssuanceType)
        .couponIssuerType(this.couponIssuerType)
        .couponIssuerId(this.couponIssuerId)
        .maxIssuance(this.maxIssuance)
        .couponUsageTargetType(this.couponUsageTargetType)
        .couponUsageTargetId(this.couponUsageTargetId)
        .discountType(this.discountType)
        .discountValue(this.discountValue)
        .expirationDays(this.expirationDays)
        .expirationDate(this.expirationDate)
        .build();
  }
}
