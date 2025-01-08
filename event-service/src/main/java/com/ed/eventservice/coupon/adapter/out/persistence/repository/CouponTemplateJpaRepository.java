package com.ed.eventservice.coupon.adapter.out.persistence.repository;

import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponTemplateJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponTemplateJpaRepository extends JpaRepository<CouponTemplateJpaEntity, Long> {

  Optional<CouponTemplateJpaEntity> findByPublicId(String couponTemplateId);
}
