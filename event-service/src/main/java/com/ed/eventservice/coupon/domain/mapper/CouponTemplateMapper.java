package com.ed.eventservice.coupon.domain.mapper;

import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponTemplateJpaEntity;
import com.ed.eventservice.coupon.application.port.in.CreateCouponTemplateCommand;
import com.ed.eventservice.coupon.application.port.out.dto.CreateCouponTemplateResponse;
import com.ed.eventservice.coupon.domain.CouponTemplate;
import java.time.Duration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", imports = {Duration.class})
public interface CouponTemplateMapper {

  @Mapping(target = "couponIssueInfo.couponIssuanceType", source = "couponIssuanceType")
  @Mapping(target = "couponIssueInfo.couponIssuerType", source = "couponIssuerType")
  @Mapping(target = "couponIssueInfo.couponIssuerId", source = "couponIssuerId")
  @Mapping(target = "couponIssueInfo.maxIssuance", source = "maxIssuance")
  @Mapping(target = "couponIssueInfo.isIssuable", source = "isIssuable")
  @Mapping(target = "couponUsageTargetInfo.couponUsageTargetType", source = "couponUsageTargetType")
  @Mapping(target = "couponUsageTargetInfo.couponUsageTargetId", source = "couponUsageTargetId")
  @Mapping(target = "couponDiscountInfo.discountType", source = "discountType")
  @Mapping(target = "couponDiscountInfo.discountValue", source = "discountValue")
  @Mapping(target = "couponExpirationInfo.expirationDays", source = "expirationDays", qualifiedByName = "toDuration")
  @Mapping(target = "couponExpirationInfo.fixedExpirationDate", source = "expirationDate")
  CouponTemplate commandToDomain(CreateCouponTemplateCommand command);

  @Named("toDuration")
  default Duration toDuration(Integer days) {
    return Duration.ofDays(days);
  }

  @Mapping(target = "couponIssueInfoJpaEntity", source = "couponIssueInfo")
  @Mapping(target = "couponUsageTargetInfoJpaEntity", source = "couponUsageTargetInfo")
  @Mapping(target = "couponDiscountInfoJpaEntity", source = "couponDiscountInfo")
  @Mapping(target = "couponExpirationInfoJpaEntity", source = "couponExpirationInfo")
  CouponTemplateJpaEntity domainToJpaEntity(CouponTemplate couponTemplate);

  @Mapping(target = "couponIssueInfo", source = "couponTemplateJpaEntity.couponIssueInfoJpaEntity")
  @Mapping(target = "couponUsageTargetInfo", source = "couponTemplateJpaEntity.couponUsageTargetInfoJpaEntity")
  @Mapping(target = "couponDiscountInfo", source = "couponTemplateJpaEntity.couponDiscountInfoJpaEntity")
  @Mapping(target = "couponExpirationInfo", source = "couponTemplateJpaEntity.couponExpirationInfoJpaEntity")
  CouponTemplate jpaEntityToDomain(CouponTemplateJpaEntity couponTemplateJpaEntity);

  @Mapping(target = "couponIssuanceType", source = "couponIssueInfo.couponIssuanceType")
  @Mapping(target = "couponIssuerType", source = "couponIssueInfo.couponIssuerType")
  @Mapping(target = "couponIssuerId", source = "couponIssueInfo.couponIssuerId")
  @Mapping(target = "maxIssuance", source = "couponIssueInfo.maxIssuance")
  @Mapping(target = "isIssuable", source = "couponIssueInfo.isIssuable")
  @Mapping(target = "couponUsageTargetType", source = "couponUsageTargetInfo.couponUsageTargetType")
  @Mapping(target = "couponUsageTargetId", source = "couponUsageTargetInfo.couponUsageTargetId")
  @Mapping(target = "discountType", source = "couponDiscountInfo.discountType")
  @Mapping(target = "discountValue", source = "couponDiscountInfo.discountValue")
  @Mapping(target = "expirationDays", source = "couponExpirationInfo.expirationDays", qualifiedByName = "toDays")
  @Mapping(target = "expirationDate", source = "couponExpirationInfo.fixedExpirationDate")
  CreateCouponTemplateResponse domainToResponse(CouponTemplate couponTemplate);

  @Named("toDays")
  default Long toDays(Duration days) {
    return days.toDays();
  }
}
