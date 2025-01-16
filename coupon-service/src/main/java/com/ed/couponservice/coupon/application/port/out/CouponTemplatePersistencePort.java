package com.ed.couponservice.coupon.application.port.out;

import com.ed.couponservice.coupon.domain.CouponTemplate;
import java.util.UUID;

public interface CouponTemplatePersistencePort {

  CouponTemplate saveCouponTemplate(CouponTemplate couponTemplate);

  CouponTemplate getCouponTemplateByPublicId(UUID couponTemplateId);
}
