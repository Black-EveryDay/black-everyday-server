package com.ed.couponservice.coupon.domain;

import com.ed.couponservice.coupon.domain.enums.CouponState;
import com.ed.couponservice.coupon.domain.enums.CouponStatusChangeReason;
import com.ed.couponservice.coupon.domain.enums.CouponUsageTargetType;
import com.ed.couponservice.coupon.domain.vo.CouponStatusChangeLog;
import com.ed.couponservice.libs.exception.DomainException;
import com.ed.couponservice.libs.exception.ExceptionStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
  private final LocalDateTime expirationDate;
  private final List<CouponStatusChangeLog> couponStatusChangeLogs;
  private LocalDateTime issuedAt;
  private UUID userId;
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
    this.couponStatusChangeLogs =
        Objects.isNull(couponStatusChangeLogs) ? new ArrayList<>() : couponStatusChangeLogs;
  }

  public void useCoupon(UUID userId, UUID brandId, UUID productId, String orderId) {

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

  public CouponUsageTargetType getCouponUsageTargetType() {

    return this.couponTemplate.getCouponUsageTargetInfo().getCouponUsageTargetType();
  }

  private void checkCouponUsageTarget(UUID brandId, UUID productId) {

    if (this.getCouponUsageTargetType().isNotTargetType(brandId, productId)) {

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

  public void issueCoupon(UUID userId) {

    if (this.state != CouponState.CREATED) {

      throw new DomainException(ExceptionStatus.COUPON_NOT_ISSUABLE);
    }

    if (Objects.nonNull(this.userId)) {

      throw new DomainException(ExceptionStatus.COUPON_ALREADY_ASSIGNED);
    }

    this.userId = userId;
    this.issuedAt = LocalDateTime.now();
    this.couponStatusChangeLogs.add(
        CouponStatusChangeLog.builder()
            .couponId(this.id)
            .beforeStatus(this.state)
            .afterStatus(CouponState.ISSUED)
            .changedAt(LocalDateTime.now())
            .reason(CouponStatusChangeReason.ISSUED)
            .build()
    );
    this.state = CouponState.ISSUED;
  }
}
