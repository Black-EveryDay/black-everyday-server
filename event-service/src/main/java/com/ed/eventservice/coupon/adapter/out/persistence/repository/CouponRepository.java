package com.ed.eventservice.coupon.adapter.out.persistence.repository;

import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponJpaEntity;
import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponStatusChangeLogJpaEntity;
import com.ed.eventservice.coupon.adapter.out.persistence.entity.CouponTemplateJpaEntity;
import com.ed.eventservice.coupon.application.port.out.CouponPersistencePort;
import com.ed.eventservice.coupon.domain.Coupon;
import com.ed.eventservice.coupon.domain.CouponTemplate;
import com.ed.eventservice.coupon.domain.mapper.CouponMapper;
import com.ed.eventservice.coupon.domain.mapper.CouponStatusChangeLogMapper;
import com.ed.eventservice.coupon.domain.mapper.CouponTemplateMapper;
import com.ed.eventservice.coupon.domain.vo.CouponStatusChangeLog;
import com.ed.eventservice.libs.exception.AdapterException;
import com.ed.eventservice.libs.exception.ExceptionStatus;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRepository implements CouponPersistencePort {

  private final CouponMapper couponMapper;
  private final CouponTemplateMapper couponTmplateMapper;
  private final CouponStatusChangeLogMapper couponStatusChangeLogMapper;
  private final CouponJpaRepository couponJpaRepository;
  private final CouponTemplateJpaRepository couponTemplateJpaRepository;
  private final CouponStatusChangeLogJpaRepository couponStatusChangeLogJpaRepository;

  @Override
  public Coupon getCouponByPublicId(UUID couponPublicId) {

    CouponJpaEntity couponJpaEntity =
        couponJpaRepository.findByPublicId(couponPublicId.toString())
            .orElseThrow(() -> new AdapterException(ExceptionStatus.COUPON_NOT_FOUND));

    CouponTemplateJpaEntity couponTemplateJpaEntity =
        couponTemplateJpaRepository.findByPublicId(couponJpaEntity.getCouponTemplateId())
            .orElseThrow(() -> new AdapterException(ExceptionStatus.COUPON_TEMPLATE_NOT_FOUND));

    CouponTemplate couponTemplate = CouponTemplate.builder()
        .createCouponTemplateDto(couponTmplateMapper.jpaEntityToCreateDto(couponTemplateJpaEntity))
        .build();

    List<CouponStatusChangeLogJpaEntity> couponStatusChangeLogJpaEntity =
        couponStatusChangeLogJpaRepository.findByCouponId(couponJpaEntity.getId());

    return couponMapper.jpaEntityToDomain(
        couponJpaEntity,
        couponTemplate,
        couponStatusChangeLogJpaEntity
    );
  }

  @Override
  public void saveNewCoupons(List<Coupon> newCoupons) {

    List<CouponJpaEntity> newCouponJpaEntities =
        newCoupons.stream()
            .map(couponMapper::domainToJpaEntity)
            .toList();

    couponJpaRepository.saveAll(newCouponJpaEntities);
  }

  @Override
  public void updateCouponStatus(Coupon coupon) {

    CouponJpaEntity couponJpaEntity = couponMapper.domainToJpaEntity(coupon);

    List<CouponStatusChangeLog> couponStatusChangeLogs = coupon.getCouponStatusChangeLogs();

    List<CouponStatusChangeLogJpaEntity> newCouponStatusChangeLogJpaEntities =
        couponStatusChangeLogs.stream()
            .filter(couponStatusChangeLog -> Objects.isNull(couponStatusChangeLog.getId()))
            .map(couponStatusChangeLogMapper::domainToJpaEntity)
            .toList();

    couponStatusChangeLogJpaRepository.saveAll(newCouponStatusChangeLogJpaEntities);
    couponJpaRepository.save(couponJpaEntity);
  }
}
