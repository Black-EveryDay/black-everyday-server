package com.ed.eventservice.coupon.application.port.in;

import com.ed.eventservice.coupon.application.port.out.dto.CreateCouponTemplateResponse;

public interface CouponTemplateUseCase {

  CreateCouponTemplateResponse createCouponTemplate(
      CreateCouponTemplateCommand createCouponTemplateCommand);

  void createCoupon(CreateCouponCommand command);
}
