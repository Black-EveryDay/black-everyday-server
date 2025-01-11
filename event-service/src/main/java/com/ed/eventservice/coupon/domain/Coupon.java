package com.ed.eventservice.coupon.domain;

import com.ed.eventservice.coupon.domain.enums.CouponState;
import com.ed.eventservice.coupon.domain.enums.CouponStatusChangeReason;
import com.ed.eventservice.coupon.domain.vo.CouponStatusChangeLog;
import com.ed.eventservice.libs.exception.DomainException;
import com.ed.eventservice.libs.exception.ExceptionStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Coupon {

  private final Long id;
  private final UUID publicId;
  private final CouponTemplate couponTemplate;
  private final UUID userId;
  private final LocalDateTime expirationDate;
  private final LocalDateTime issuedAt;
  private final List<CouponStatusChangeLog> couponStatusChangeLogs;
  private CouponState state;

  @Builder
  private Coupon(Long id, UUID publicId, CouponTemplate couponTemplate, UUID userId,
      CouponState state, LocalDateTime expirationDate, LocalDateTime issuedAt,
      List<CouponStatusChangeLog> couponStatusChangeLogs) {
    this.id = id;
    this.publicId = publicId;
    this.couponTemplate = couponTemplate;
    this.userId = userId;
    this.state = Objects.isNull(state) ? CouponState.CREATED : state;
    this.expirationDate = expirationDate;
    this.issuedAt = issuedAt;
    this.couponStatusChangeLogs = couponStatusChangeLogs;
  }

  public void useCoupon(UUID userId, UUID brandId, UUID productId, UUID orderId) {

    checkCouponOwner(userId);
    checkCouponUsable();
    checkCouponUsageTarget(brandId, productId);

    this.state = CouponState.REDEEMED;
    this.couponStatusChangeLogs.add(
        CouponStatusChangeLog.builder()
            .couponId(this.id)
            .beforeStatus(this.state)
            .afterStatus(CouponState.REDEEMED)
            .changedAt(LocalDateTime.now())
            .reason(CouponStatusChangeReason.USED)
            .orderId(orderId)
            .build()
    );
  }

  private void checkCouponOwner(UUID userId) {

    if (Objects.isNull(this.userId) || Objects.isNull(userId) || !this.userId.equals(userId)) {

      throw new DomainException(ExceptionStatus.USER_NOT_OWNER_OF_COUPON);
    }
  }

  private void checkCouponUsable() {

    if (this.state != CouponState.ISSUED) {

      throw new DomainException(ExceptionStatus.COUPON_NOT_USABLE);
    }

    if (this.expirationDate.isBefore(LocalDateTime.now())) {

      throw new DomainException(ExceptionStatus.COUPON_EXPIRED);
    }
  }

  private void checkCouponUsageTarget(UUID brandId, UUID productId) {

    switch (this.couponTemplate.getCouponUsageTargetInfo().getCouponUsageTargetType()) {

      case ALL:
        break;

      case BRAND:
        checkBrandCouponUsageTarget(brandId);
        break;

      case PRODUCT:
        checkProductCouponUsageTarget(productId);
        break;

      default:
        throw new DomainException(ExceptionStatus.COUPON_USAGE_TARGET_TYPE_NOT_SUPPORTED);
    }
  }

  private void checkBrandCouponUsageTarget(UUID brandId) {

    if (!this.couponTemplate.getCouponUsageTargetInfo().getCouponUsageTargetId().equals(brandId)) {

      throw new DomainException(ExceptionStatus.COUPON_USAGE_TARGET_NOT_MATCHED);
    }
  }

  private void checkProductCouponUsageTarget(UUID productId) {

    if (productId.equals(
        this.couponTemplate.getCouponUsageTargetInfo().getCouponUsageTargetId())) {

      throw new DomainException(ExceptionStatus.COUPON_USAGE_TARGET_NOT_MATCHED);
    }
  }

  public void cancelUseCoupon() {

    if (this.state != CouponState.REDEEMED) {

      throw new DomainException(ExceptionStatus.COUPON_NOT_REDEEMED);
    }

    this.state = CouponState.ISSUED;
    this.couponStatusChangeLogs.add(
        CouponStatusChangeLog.builder()
            .couponId(this.id)
            .beforeStatus(CouponState.REDEEMED)
            .afterStatus(CouponState.ISSUED)
            .changedAt(LocalDateTime.now())
            .reason(CouponStatusChangeReason.CANCELED)
            .build()
    );
  }
}
