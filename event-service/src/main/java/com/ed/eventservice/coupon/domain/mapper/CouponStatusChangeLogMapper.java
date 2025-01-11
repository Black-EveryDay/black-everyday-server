package com.ed.eventservice.coupon.domain.mapper;

import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponStatusChangeLogJpaEntity;
import com.ed.eventservice.coupon.domain.vo.CouponStatusChangeLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CouponStatusChangeLogMapper {

  CouponStatusChangeLogJpaEntity domainToJpaEntity(CouponStatusChangeLog couponStatusChangeLog);

  CouponStatusChangeLog jpaEntityToDomain(
      CouponStatusChangeLogJpaEntity couponStatusChangeLogJpaEntity);
}
