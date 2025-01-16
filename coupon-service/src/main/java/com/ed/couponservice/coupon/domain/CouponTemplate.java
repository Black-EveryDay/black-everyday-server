package com.ed.couponservice.coupon.domain;

import com.ed.couponservice.coupon.domain.dto.CreateCouponTemplateDto;
import com.ed.couponservice.coupon.domain.enums.CouponIssuerType;
import com.ed.couponservice.coupon.domain.enums.CouponUsageTargetType;
import com.ed.couponservice.coupon.domain.vo.CouponDiscountInfo;
import com.ed.couponservice.coupon.domain.vo.CouponExpirationInfo;
import com.ed.couponservice.coupon.domain.vo.CouponIssueInfo;
import com.ed.couponservice.coupon.domain.vo.CouponUsageTargetInfo;
import com.ed.couponservice.libs.exception.DomainException;
import com.ed.couponservice.libs.exception.ExceptionStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponTemplate {

  private final Long id;
  private final UUID publicId;
  private final String couponName;
  private final CouponIssueInfo couponIssueInfo;
  private final CouponUsageTargetInfo couponUsageTargetInfo;
  private final CouponDiscountInfo couponDiscountInfo;
  private final CouponExpirationInfo couponExpirationInfo;

  @Builder
  private CouponTemplate(CreateCouponTemplateDto createCouponTemplateDto) {
    validityCheck(createCouponTemplateDto);
    this.id = createCouponTemplateDto.getId();
    this.publicId = createCouponTemplateDto.getPublicId();
    this.couponName = createCouponTemplateDto.getCouponName();
    this.couponIssueInfo = createCouponTemplateDto.getCouponIssueInfo();
    this.couponUsageTargetInfo = createCouponTemplateDto.getCouponUsageTargetInfo();
    this.couponDiscountInfo = createCouponTemplateDto.getCouponDiscountInfo();
    this.couponExpirationInfo = createCouponTemplateDto.getCouponExpirationInfo();
  }

  private void validityCheck(CreateCouponTemplateDto createCouponTemplateDto) {

    checkCouponIssuerTypeIsBrandAndIssuerIdIsNotNull(createCouponTemplateDto.getCouponIssueInfo());

    checkCouponUsageTargetTypeIsNotAllAndUsageTargetIdIsNotNull(
        createCouponTemplateDto.getCouponUsageTargetInfo());

    checkMaxIssuanceIsNullOrGreaterThanZero(createCouponTemplateDto.getCouponIssueInfo());
  }

  private void checkCouponIssuerTypeIsBrandAndIssuerIdIsNotNull(CouponIssueInfo couponIssueInfo) {

    if (couponIssueInfo.getCouponIssuerType() == CouponIssuerType.BRAND
        && Objects.isNull(couponIssueInfo.getCouponIssuerId())) {

      throw new DomainException(ExceptionStatus.INVALID_BRAND_ID);
    }
  }

  private void checkMaxIssuanceIsNullOrGreaterThanZero(CouponIssueInfo couponIssueInfo) {

    if (Objects.nonNull(couponIssueInfo.getMaxIssuance())
        && couponIssueInfo.getMaxIssuance() <= 0) {

      throw new DomainException(ExceptionStatus.INVALID_MAX_ISSUANCE);
    }
  }

  private void checkCouponUsageTargetTypeIsNotAllAndUsageTargetIdIsNotNull(
      CouponUsageTargetInfo couponUsageTargetInfo) {

    if (couponUsageTargetInfo.getCouponUsageTargetType() != CouponUsageTargetType.ALL
        && Objects.isNull(couponUsageTargetInfo.getCouponUsageTargetId())) {

      throw new DomainException(ExceptionStatus.INVALID_USAGE_TARGET_ID);
    }
  }


  public List<Coupon> createCoupon(Integer quantity) {

    checkQuantityGreaterThanZero(quantity);
    checkMaxIssuanceLimit(quantity);

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime expirationDate = computeCouponExpiryDate(now);

    return IntStream.range(0, quantity).mapToObj(
        i -> Coupon.builder()
            .publicId(UUID.randomUUID())
            .couponTemplate(this)
            .issuedAt(now)
            .expirationDate(expirationDate)
            .build()).toList();
  }

  private void checkQuantityGreaterThanZero(Integer quantity) {

    if (quantity <= 0) {

      throw new DomainException(ExceptionStatus.INVALID_QUANTITY);
    }
  }

  private void checkMaxIssuanceLimit(Integer quantity) {

    if (this.couponIssueInfo.getMaxIssuance() != null
        && this.couponIssueInfo.getMaxIssuance() < this.couponIssueInfo.getIssuedCount() + quantity
    ) {

      throw new DomainException(ExceptionStatus.MAX_ISSUANCE_EXCEEDED);
    }
  }

  private LocalDateTime computeCouponExpiryDate(LocalDateTime localDateTime) {

    if (Objects.isNull(this.couponExpirationInfo.getExpirationDays()) && Objects.isNull(
        this.couponExpirationInfo.getFixedExpirationDate())) {

      return null;
    }

    if (Objects.isNull(this.couponExpirationInfo.getExpirationDays())) {

      return this.couponExpirationInfo.getFixedExpirationDate();
    }

    if (Objects.isNull(this.couponExpirationInfo.getFixedExpirationDate())) {

      return localDateTime.plusDays(this.couponExpirationInfo.getExpirationDays().toDays());
    }

    return this.couponExpirationInfo.getFixedExpirationDate().isBefore(localDateTime)
        ? this.couponExpirationInfo.getFixedExpirationDate()
        : localDateTime.plusDays(this.couponExpirationInfo.getExpirationDays().toDays());
  }
}
