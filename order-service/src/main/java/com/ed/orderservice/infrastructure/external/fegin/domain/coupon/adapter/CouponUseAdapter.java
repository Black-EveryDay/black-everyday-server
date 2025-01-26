package com.ed.orderservice.infrastructure.external.fegin.domain.coupon.adapter;

import com.ed.orderservice.application.port.in.CouponUseInPort;
import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.CouponClient;
import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.dto.UseCouponRequest;
import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.dto.UseCouponResponse;
import com.ed.orderservice.libs.response.ApiResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponUseAdapter implements CouponUseInPort {

  private final CouponClient couponClient;

  @Override
  public ApiResponse<UseCouponResponse> useCoupon(UUID couponPublicId,
      UseCouponRequest useCouponRequest) {

    return couponClient.useCoupon(couponPublicId, useCouponRequest);
  }

  @Override
  public ApiResponse<UseCouponResponse> cancelUseCoupon(UUID couponPublicId) {
    
    return couponClient.cancelUseCoupon(couponPublicId);
  }

}
