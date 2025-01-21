package com.ed.couponservice.coupon.application.port.in.command;

import com.ed.couponservice.coupon.domain.enums.CouponIssuanceType;
import com.ed.couponservice.coupon.domain.enums.CouponIssuerType;
import com.ed.couponservice.coupon.domain.enums.CouponUsageTargetType;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
public class SearchCouponTemplatesCommand {

  private UUID publicId;
  private String couponName;
  private CouponIssuanceType couponIssuanceType;
  private CouponIssuerType couponIssuerType;
  private UUID couponIssuerId;
  private Boolean isIssuable;
  private CouponUsageTargetType couponUsageTargetType;
  private UUID couponUsageTargetId;
  private Pageable pageable;
}
