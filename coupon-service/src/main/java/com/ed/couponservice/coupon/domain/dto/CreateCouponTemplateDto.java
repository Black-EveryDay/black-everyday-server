package com.ed.couponservice.coupon.domain.dto;

import com.ed.couponservice.coupon.domain.vo.CouponDiscountInfo;
import com.ed.couponservice.coupon.domain.vo.CouponExpirationInfo;
import com.ed.couponservice.coupon.domain.vo.CouponIssueInfo;
import com.ed.couponservice.coupon.domain.vo.CouponUsageTargetInfo;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateCouponTemplateDto {

  Long id;
  UUID publicId;
  String couponName;
  CouponIssueInfo couponIssueInfo;
  CouponUsageTargetInfo couponUsageTargetInfo;
  CouponDiscountInfo couponDiscountInfo;
  CouponExpirationInfo couponExpirationInfo;

  @Builder
  private CreateCouponTemplateDto(
      Long id,
      UUID publicId,
      String couponName,
      CouponIssueInfo couponIssueInfo,
      CouponUsageTargetInfo couponUsageTargetInfo,
      CouponDiscountInfo couponDiscountInfo,
      CouponExpirationInfo couponExpirationInfo
  ) {
    this.id = id;
    this.publicId = publicId;
    this.couponName = couponName;
    this.couponIssueInfo = couponIssueInfo;
    this.couponUsageTargetInfo = couponUsageTargetInfo;
    this.couponDiscountInfo = couponDiscountInfo;
    this.couponExpirationInfo = couponExpirationInfo;
  }
}
