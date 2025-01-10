package com.ed.eventservice.coupon.domain.mapper;

import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponJpaEntity;
import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponStatusChangeLogJpaEntity;
import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponTemplateJpaEntity;
import com.ed.eventservice.coupon.domain.Coupon;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouponMapper {

  @Mapping(target = "couponTemplateId", source = "coupon.couponTemplate.publicId")
  CouponJpaEntity domainToJpaEntity(Coupon coupon);

  @Mapping(target = "couponTemplate", source = "couponTemplateJpaEntity")
  @Mapping(target = "id", source = "couponJpaEntity.id")
  @Mapping(target = "publicId", source = "couponJpaEntity.publicId")
  Coupon jpaEntityToDomain(CouponJpaEntity couponJpaEntity,
      CouponTemplateJpaEntity couponTemplateJpaEntity,
      List<CouponStatusChangeLogJpaEntity> couponStatusChangelogs);
}
