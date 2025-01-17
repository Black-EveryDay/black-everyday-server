package com.ed.couponservice.coupon.application.port.in;

import com.ed.couponservice.coupon.application.port.in.command.CouponCancelUseCommand;
import com.ed.couponservice.coupon.application.port.in.command.CouponUseCommand;
import com.ed.couponservice.coupon.application.port.in.command.IssueCouponCommand;
import com.ed.couponservice.coupon.application.port.out.dto.IssueCouponResponse;
import com.ed.couponservice.coupon.application.port.out.dto.UseCouponResponse;

public interface CouponUseCase {

  UseCouponResponse useCoupon(CouponUseCommand command);

  UseCouponResponse cancelUseCoupon(CouponCancelUseCommand build);

  IssueCouponResponse issueCoupon(IssueCouponCommand command);
}
