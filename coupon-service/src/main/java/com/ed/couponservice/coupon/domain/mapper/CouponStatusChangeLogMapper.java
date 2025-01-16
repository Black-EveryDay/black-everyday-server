package com.ed.couponservice.coupon.domain.mapper;

import com.ed.couponservice.coupon.adapter.out.persistence.entity.CouponStatusChangeLogJpaEntity;
import com.ed.couponservice.coupon.domain.vo.CouponStatusChangeLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CouponStatusChangeLogMapper {

  CouponStatusChangeLogJpaEntity domainToJpaEntity(CouponStatusChangeLog couponStatusChangeLog);

  CouponStatusChangeLog jpaEntityToDomain(
      CouponStatusChangeLogJpaEntity couponStatusChangeLogJpaEntity);
}
