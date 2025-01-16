package com.ed.couponservice.coupon.application.port.in;

import com.ed.couponservice.coupon.application.port.out.dto.CreateCouponTemplateResponse;

public interface CouponTemplateUseCase {

  CreateCouponTemplateResponse createCouponTemplate(
      CreateCouponTemplateCommand createCouponTemplateCommand
  );

  void createCoupon(CreateCouponCommand command);
}
