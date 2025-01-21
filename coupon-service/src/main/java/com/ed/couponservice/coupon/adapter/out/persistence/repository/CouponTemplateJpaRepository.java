package com.ed.couponservice.coupon.adapter.out.persistence.repository;

import com.ed.couponservice.coupon.adapter.out.persistence.entity.CouponTemplateJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CouponTemplateJpaRepository extends JpaRepository<CouponTemplateJpaEntity, Long>,
    QuerydslPredicateExecutor<CouponTemplateJpaEntity> {

  Optional<CouponTemplateJpaEntity> findByPublicId(String couponTemplateId);
}
