package com.ed.eventservice.coupon.domain.dto;

import com.ed.eventservice.coupon.domain.vo.CouponDiscountInfo;
import com.ed.eventservice.coupon.domain.vo.CouponExpirationInfo;
import com.ed.eventservice.coupon.domain.vo.CouponIssueInfo;
import com.ed.eventservice.coupon.domain.vo.CouponUsageTargetInfo;
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
