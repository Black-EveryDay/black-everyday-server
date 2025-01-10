package com.ed.eventservice.coupon.application.service;

import com.ed.eventservice.coupon.application.port.in.CouponUseCase;
import com.ed.eventservice.coupon.application.port.in.CouponUseCommand;
import com.ed.eventservice.coupon.application.port.out.CouponPersistencePort;
import com.ed.eventservice.coupon.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService implements CouponUseCase {

  private final CouponPersistencePort couponPersistencePort;

  @Override
  @Transactional
  public void useCoupon(CouponUseCommand command) {

    Coupon coupon = couponPersistencePort.getCouponByPublicId(command.getCouponId());

    coupon.useCoupon(command.getUserId(), command.getBrandId(), command.getProductId(),
        command.getOrderId());

    couponPersistencePort.updateCouponStatus(coupon);
  }
}
