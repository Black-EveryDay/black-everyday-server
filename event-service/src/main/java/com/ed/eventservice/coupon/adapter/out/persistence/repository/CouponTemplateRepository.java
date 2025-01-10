package com.ed.eventservice.coupon.adapter.out.persistence.repository;

import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponTemplateJpaEntity;
import com.ed.eventservice.coupon.application.port.out.CouponTemplatePersistencePort;
import com.ed.eventservice.coupon.domain.Coupon;
import com.ed.eventservice.coupon.domain.CouponTemplate;
import com.ed.eventservice.coupon.domain.mapper.CouponMapper;
import com.ed.eventservice.coupon.domain.mapper.CouponTemplateMapper;
import com.ed.eventservice.libs.exception.AdapterException;
import com.ed.eventservice.libs.exception.ExceptionStatus;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponTemplateRepository implements CouponTemplatePersistencePort {

  private final CouponTemplateMapper couponTemplateMapper;
  private final CouponTemplateJpaRepository couponTemplateJpaRepository;
  private final CouponMapper couponMapper;
  private final CouponJpaRepository couponJpaRepository;

  @Override
  public CouponTemplate saveCouponTemplate(CouponTemplate couponTemplate) {
    CouponTemplateJpaEntity couponTemplateJpaEntity = couponTemplateJpaRepository.save(
        couponTemplateMapper.domainToJpaEntity(couponTemplate)
    );

    return couponTemplateMapper.jpaEntityToDomain(couponTemplateJpaEntity, null);
  }

  @Override
  public CouponTemplate getCouponTemplateByPublicId(UUID couponTemplateId) {
    CouponTemplateJpaEntity couponTemplateJpaEntity = couponTemplateJpaRepository.findByPublicId(
            couponTemplateId.toString())
        .orElseThrow(() -> new AdapterException(ExceptionStatus.COUPON_TEMPLATE_NOT_FOUND));

    List<Coupon> coupons = couponJpaRepository.findByCouponTemplateId(
            couponTemplateJpaEntity.getPublicId()).stream().map(couponMapper::jpaEntityToDomain)
        .toList();

    return couponTemplateMapper.jpaEntityToDomain(couponTemplateJpaEntity, coupons);
  }

  @Override
  public void updateCoupon(CouponTemplate couponTemplate) {
    couponTemplate.getCoupons()
        .stream().filter(coupon -> coupon.getId() == null)
        .forEach(coupon ->
            couponJpaRepository.save(couponMapper.domainToJpaEntity(coupon))
        );
  }
}
