package com.ed.eventservice.coupon.application.port.out;

import com.ed.eventservice.coupon.domain.CouponTemplate;

public interface CouponTemplatePersistencePort {

  CouponTemplate saveCouponTemplate(CouponTemplate couponTemplate);
}
