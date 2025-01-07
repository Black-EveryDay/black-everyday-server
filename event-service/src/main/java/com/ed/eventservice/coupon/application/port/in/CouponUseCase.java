package com.ed.eventservice.coupon.application.port.in;

import com.ed.eventservice.coupon.application.port.out.dto.CreateCouponTemplateResponse;

public interface CouponUseCase {

  CreateCouponTemplateResponse createCouponTemplate(
      CreateCouponTemplateCommand createCouponTemplateCommand);
}
