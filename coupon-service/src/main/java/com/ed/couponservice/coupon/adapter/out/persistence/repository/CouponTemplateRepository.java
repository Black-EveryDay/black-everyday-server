package com.ed.couponservice.coupon.adapter.out.persistence.repository;

import com.ed.couponservice.coupon.adapter.out.persistence.entity.CouponTemplateJpaEntity;
import com.ed.couponservice.coupon.adapter.out.persistence.entity.QCouponTemplateJpaEntity;
import com.ed.couponservice.coupon.application.port.in.command.SearchCouponTemplatesCommand;
import com.ed.couponservice.coupon.application.port.out.CouponTemplatePersistencePort;
import com.ed.couponservice.coupon.domain.CouponTemplate;
import com.ed.couponservice.coupon.domain.mapper.CouponMapper;
import com.ed.couponservice.coupon.domain.mapper.CouponTemplateMapper;
import com.ed.couponservice.libs.exception.AdapterException;
import com.ed.couponservice.libs.exception.ExceptionStatus;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    CouponTemplateJpaEntity couponTemplateJpaEntity =
        couponTemplateJpaRepository.save(couponTemplateMapper.domainToJpaEntity(couponTemplate));

    return CouponTemplate.builder()
        .createCouponTemplateDto(couponTemplateMapper.jpaEntityToCreateDto(couponTemplateJpaEntity))
        .build();
  }

  @Override
  public CouponTemplate getCouponTemplateByPublicId(UUID couponTemplateId) {

    CouponTemplateJpaEntity couponTemplateJpaEntity =
        couponTemplateJpaRepository.findByPublicId(couponTemplateId.toString())
            .orElseThrow(() -> new AdapterException(ExceptionStatus.COUPON_TEMPLATE_NOT_FOUND));

    return CouponTemplate.builder()
        .createCouponTemplateDto(couponTemplateMapper.jpaEntityToCreateDto(couponTemplateJpaEntity))
        .build();
  }

  @Override
  public Page<CouponTemplate> searchCouponTemplates(SearchCouponTemplatesCommand command) {
    QCouponTemplateJpaEntity couponTemplateJpaEntity = QCouponTemplateJpaEntity.couponTemplateJpaEntity;

    Predicate predicate = ExpressionUtils.allOf(
        couponTemplateJpaEntity.isDeleted.eq(false),
        Objects.nonNull(command.getPublicId()) ? couponTemplateJpaEntity.publicId.eq(
            command.getPublicId().toString()) : null,
        Objects.nonNull(command.getCouponName()) ?
            couponTemplateJpaEntity.couponName.containsIgnoreCase(command.getCouponName()) : null,
        Objects.nonNull(command.getCouponIssuanceType()) ?
            couponTemplateJpaEntity.couponIssueInfoJpaEntity.couponIssuanceType.eq(
                command.getCouponIssuanceType()) : null,
        Objects.nonNull(command.getCouponIssuerType()) ?
            couponTemplateJpaEntity.couponIssueInfoJpaEntity.couponIssuerType.eq(
                command.getCouponIssuerType()) : null,
        Objects.nonNull(command.getCouponIssuerId()) ?
            couponTemplateJpaEntity.couponIssueInfoJpaEntity.couponIssuerId.eq(
                command.getCouponIssuerId().toString()) : null,
        Objects.nonNull(command.getIsIssuable()) ?
            couponTemplateJpaEntity.couponIssueInfoJpaEntity.isIssuable.eq(command.getIsIssuable())
            : null,
        Objects.nonNull(command.getCouponUsageTargetType()) ?
            couponTemplateJpaEntity.couponUsageTargetInfoJpaEntity.couponUsageTargetType.eq(
                command.getCouponUsageTargetType()) : null,
        Objects.nonNull(command.getCouponUsageTargetId()) ?
            couponTemplateJpaEntity.couponUsageTargetInfoJpaEntity.couponUsageTargetId.eq(
                command.getCouponUsageTargetId().toString()) : null
    );

    return couponTemplateJpaRepository.findAll(predicate, command.getPageable())
        .map(couponTemplateMapper::jpaEntityToDomain);
  }
}
