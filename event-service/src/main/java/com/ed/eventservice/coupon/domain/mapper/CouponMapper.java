package com.ed.eventservice.coupon.domain.mapper;

import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponJpaEntity;
import com.ed.eventservice.coupon.domain.Coupon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CouponMapper {

  CouponJpaEntity domainToJpaEntity(Coupon coupon);

  Coupon jpaEntityToDomain(CouponJpaEntity couponJpaEntity);
}
