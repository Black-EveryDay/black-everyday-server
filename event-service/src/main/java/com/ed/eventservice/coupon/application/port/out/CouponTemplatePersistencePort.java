package com.ed.eventservice.coupon.application.port.out;

import com.ed.eventservice.coupon.domain.CouponTemplate;
import java.util.UUID;

public interface CouponTemplatePersistencePort {

  CouponTemplate saveCouponTemplate(CouponTemplate couponTemplate);

  CouponTemplate getCouponTemplateByPublicId(UUID couponTemplateId);

  void updateCoupon(CouponTemplate couponTemplate);
}
