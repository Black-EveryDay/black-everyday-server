package com.ed.eventservice.coupon.adapter.out.persistence.repository;

import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponJpaEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<CouponJpaEntity, Long> {

  Optional<CouponJpaEntity> findByPublicId(String couponId);

  List<CouponJpaEntity> findByCouponTemplateId(String string);
}
