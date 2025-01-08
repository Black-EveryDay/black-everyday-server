package com.ed.eventservice.coupon.adapter.out.persistence.repository;

import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponTemplateJpaEntity;
import com.ed.eventservice.coupon.application.port.out.CouponTemplatePersistencePort;
import com.ed.eventservice.coupon.domain.CouponTemplate;
import com.ed.eventservice.coupon.domain.mapper.CouponTemplateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponTemplateRepository implements CouponTemplatePersistencePort {

  private final CouponTemplateMapper couponTemplateMapper;
  private final CouponTemplateJpaRepository couponTemplateJpaRepository;

  @Override
  public CouponTemplate saveCouponTemplate(CouponTemplate couponTemplate) {
    CouponTemplateJpaEntity couponTemplateJpaEntity = couponTemplateJpaRepository.save(
        couponTemplateMapper.domainToJpaEntity(couponTemplate)
    );

    return couponTemplateMapper.jpaEntityToDomain(couponTemplateJpaEntity);
  }
}
