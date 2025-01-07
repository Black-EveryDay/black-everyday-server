package com.ed.eventservice.coupon.adapter.out.persistence.repository;

import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponTemplateJpaEntity;
import com.ed.eventservice.coupon.application.port.out.CouponTemplatePersistencePort;
import com.ed.eventservice.coupon.domain.CouponTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponTemplateRepository implements CouponTemplatePersistencePort {

  private final CouponTemplateJpaRepository couponTemplateJpaRepository;


  @Override
  public CouponTemplate saveCouponTemplate(CouponTemplate couponTemplate) {
    CouponTemplateJpaEntity couponTemplateJpaEntity = couponTemplateJpaRepository.save(
        CouponTemplateJpaEntity.from(couponTemplate));

    return couponTemplateJpaEntity.toDomain();
  }
}
