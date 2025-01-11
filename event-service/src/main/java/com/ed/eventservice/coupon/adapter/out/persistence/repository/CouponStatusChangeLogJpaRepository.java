package com.ed.eventservice.coupon.adapter.out.persistence.repository;

import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponStatusChangeLogJpaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponStatusChangeLogJpaRepository extends
    JpaRepository<CouponStatusChangeLogJpaEntity, Long> {

  List<CouponStatusChangeLogJpaEntity> findByCouponId(Long couponId);
}
