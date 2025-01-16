package com.ed.couponservice.coupon.application.service;

import com.ed.couponservice.coupon.application.port.in.CouponCancelUseCommand;
import com.ed.couponservice.coupon.application.port.in.CouponUseCase;
import com.ed.couponservice.coupon.application.port.in.CouponUseCommand;
import com.ed.couponservice.coupon.application.port.in.IssueCouponCommand;
import com.ed.couponservice.coupon.application.port.out.CouponPersistencePort;
import com.ed.couponservice.coupon.application.port.out.dto.IssueCouponResponse;
import com.ed.couponservice.coupon.application.port.out.dto.UseCouponResponse;
import com.ed.couponservice.coupon.domain.Coupon;
import com.ed.couponservice.coupon.domain.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService implements CouponUseCase {

  private final CouponPersistencePort couponPersistencePort;
  private final CouponMapper couponMapper;

  @Override
  @Transactional
  public UseCouponResponse useCoupon(CouponUseCommand command) {

    Coupon coupon = couponPersistencePort.getCouponByPublicId(command.getCouponId());

    coupon.useCoupon(command.getUserId(), command.getBrandId(), command.getProductId(),
        command.getOrderId());

    couponPersistencePort.updateCouponStatus(coupon);

    return couponMapper.couponToUseCouponResponse(coupon);
  }

  @Override
  @Transactional
  public UseCouponResponse cancelUseCoupon(CouponCancelUseCommand command) {

    Coupon coupon = couponPersistencePort.getCouponByPublicId(command.getCouponId());

    coupon.cancelUseCoupon();

    couponPersistencePort.updateCouponStatus(coupon);

    return couponMapper.couponToUseCouponResponse(coupon);
  }

  @Override
  @Transactional
  public IssueCouponResponse issueCoupon(IssueCouponCommand command) {

    Coupon coupon = couponPersistencePort.getCouponByPublicId(command.getCouponId());

    coupon.issueCoupon(command.getUserId());

    couponPersistencePort.updateCouponStatus(coupon);

    return couponMapper.couponToIssueCouponResponse(coupon);
  }

}
