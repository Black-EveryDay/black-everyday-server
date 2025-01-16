package com.ed.eventservice.coupon.application.port.in;

import com.ed.eventservice.coupon.application.port.out.dto.IssueCouponResponse;
import com.ed.eventservice.coupon.application.port.out.dto.UseCouponResponse;

public interface CouponUseCase {

  UseCouponResponse useCoupon(CouponUseCommand command);

  UseCouponResponse cancelUseCoupon(CouponCancelUseCommand build);

  IssueCouponResponse issueCoupon(IssueCouponCommand command);
}
