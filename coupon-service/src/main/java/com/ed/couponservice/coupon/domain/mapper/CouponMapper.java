package com.ed.couponservice.coupon.domain.mapper;

import com.ed.couponservice.coupon.adapter.out.persistence.entity.CouponJpaEntity;
import com.ed.couponservice.coupon.adapter.out.persistence.entity.CouponStatusChangeLogJpaEntity;
import com.ed.couponservice.coupon.application.port.out.dto.IssueCouponResponse;
import com.ed.couponservice.coupon.application.port.out.dto.UseCouponResponse;
import com.ed.couponservice.coupon.domain.Coupon;
import com.ed.couponservice.coupon.domain.CouponTemplate;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouponMapper {

  @Mapping(target = "couponTemplateId", source = "coupon.couponTemplate.publicId")
  CouponJpaEntity domainToJpaEntity(Coupon coupon);

  @Mapping(target = "couponTemplate", source = "couponTemplate")
  @Mapping(target = "id", source = "couponJpaEntity.id")
  @Mapping(target = "publicId", source = "couponJpaEntity.publicId")
  @Mapping(target = "userId", source = "couponJpaEntity.userId")
  @Mapping(target = "couponStatusChangeLogs", source = "couponStatusChangeLogs")
  Coupon jpaEntityToDomain(CouponJpaEntity couponJpaEntity,
      CouponTemplate couponTemplate,
      List<CouponStatusChangeLogJpaEntity> couponStatusChangeLogs);

  @Mapping(target = "couponId", source = "coupon.publicId")
  @Mapping(target = "couponTemplate", source = "coupon.couponTemplate")
  @Mapping(target = "couponTemplate.templateId", source = "coupon.couponTemplate.publicId")
  @Mapping(target = "couponTemplate.discountType", source = "coupon.couponTemplate.couponDiscountInfo.discountType")
  @Mapping(target = "couponTemplate.discountValue", source = "coupon.couponTemplate.couponDiscountInfo.discountValue")
  UseCouponResponse couponToUseCouponResponse(Coupon coupon);

  @Mapping(target = "couponId", source = "coupon.publicId")
  @Mapping(target = "couponTemplate", source = "coupon.couponTemplate")
  @Mapping(target = "couponTemplate.templateId", source = "coupon.couponTemplate.publicId")
  IssueCouponResponse couponToIssueCouponResponse(Coupon coupon);
}
