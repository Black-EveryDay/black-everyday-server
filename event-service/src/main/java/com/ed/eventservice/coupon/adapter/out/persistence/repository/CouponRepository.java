package com.ed.eventservice.coupon.adapter.out.persistence.repository;

import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponJpaEntity;
import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponTemplateJpaEntity;
import com.ed.eventservice.coupon.application.port.out.CouponPersistencePort;
import com.ed.eventservice.coupon.domain.Coupon;
import com.ed.eventservice.coupon.domain.mapper.CouponMapper;
import com.ed.eventservice.libs.exception.AdapterException;
import com.ed.eventservice.libs.exception.ExceptionStatus;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRepository implements CouponPersistencePort {

  private final CouponMapper couponMapper;
  private final CouponJpaRepository couponJpaRepository;
  private final CouponTemplateJpaRepository couponTemplateJpaRepository;

  @Override
  public Coupon getCouponByPublicId(UUID couponPublicId) {
    CouponJpaEntity couponJpaEntity = couponJpaRepository.findByPublicId(couponPublicId.toString())
        .orElseThrow(() -> new AdapterException(ExceptionStatus.COUPON_NOT_FOUND));

    CouponTemplateJpaEntity couponTemplateJpaEntity = couponTemplateJpaRepository.findByPublicId(
            couponJpaEntity.getCouponTemplateId())
        .orElseThrow(() -> new AdapterException(ExceptionStatus.COUPON_TEMPLATE_NOT_FOUND));

    return couponMapper.jpaEntityToDomain(couponJpaEntity, couponTemplateJpaEntity);
  }

  @Override
  public void saveNewCoupons(List<Coupon> newCoupons) {

    List<CouponJpaEntity> newCouponJpaEntities = newCoupons.stream()
        .map(couponMapper::domainToJpaEntity).toList();

    couponJpaRepository.saveAll(newCouponJpaEntities);
  }

  @Override
  public void updateCouponStatus(Coupon coupon) {
    CouponJpaEntity couponJpaEntity = couponMapper.domainToJpaEntity(coupon);

    couponJpaRepository.save(couponJpaEntity);
  }
}
