package com.ed.eventservice.coupon.application.port.out;

import com.ed.eventservice.coupon.domain.Coupon;
import java.util.List;
import java.util.UUID;

public interface CouponPersistencePort {

  Coupon getCouponByPublicId(UUID couponPublicId);

  void saveNewCoupons(List<Coupon> newCoupons);

  void updateCouponStatus(Coupon coupon);
}
