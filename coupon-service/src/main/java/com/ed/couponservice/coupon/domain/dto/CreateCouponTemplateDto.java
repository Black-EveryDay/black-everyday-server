package com.ed.couponservice.coupon.domain.dto;

import com.ed.couponservice.coupon.domain.vo.CouponDiscountInfo;
import com.ed.couponservice.coupon.domain.vo.CouponExpirationInfo;
import com.ed.couponservice.coupon.domain.vo.CouponIssueInfo;
import com.ed.couponservice.coupon.domain.vo.CouponUsageTargetInfo;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateCouponTemplateDto {

  Long id;
  UUID publicId;
  String couponName;
  CouponIssueInfo couponIssueInfo;
  CouponUsageTargetInfo couponUsageTargetInfo;
  CouponDiscountInfo couponDiscountInfo;
  CouponExpirationInfo couponExpirationInfo;
}
