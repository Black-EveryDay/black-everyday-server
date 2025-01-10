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
@Builder
public class Coupon {

  private Long id;
  private UUID publicId;
  private CouponTemplate couponTemplate;
  private UUID userId;
  private CouponState state;
  private LocalDateTime expirationDate;
  private LocalDateTime issuedAt;
  private List<CouponStatusChangeLog> couponStatusChangelogs;

  public void useCoupon(UUID userId, UUID brandId, UUID productId, UUID orderId) {

    checkCouponOwner(userId);
    checkCouponUsable();
    checkCouponUsageTarget(brandId, productId);

    this.state = CouponState.REDEEMED;
    this.couponStatusChangelogs.add(
        CouponStatusChangeLog.builder()
            .beforeStatus(this.state)
            .afterStatus(CouponState.REDEEMED)
            .changedAt(LocalDateTime.now())
            .reason(CouponStatusChangeReason.USED)
            .orderId(orderId)
            .build()
    );
  }

  private void checkCouponOwner(UUID userId) {

    if (!Objects.isNull(this.userId) && !this.userId.equals(userId)) {

      throw new DomainException(ExceptionStatus.USER_NOT_OWNER_OF_COUPON);
    }
  }

  private void checkCouponUsable() {

    if (this.state != CouponState.ISSUED) {

      throw new IllegalArgumentException("Coupon is not in issued state");
    }

    if (this.expirationDate.isBefore(LocalDateTime.now())) {

      throw new IllegalArgumentException("Coupon is expired");
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
}
