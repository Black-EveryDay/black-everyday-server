package com.ed.orderservice.application.port.in;

import com.ed.orderservice.infrastructure.external.fegin.domain.event.dto.UseCouponRequest;
import com.ed.orderservice.infrastructure.external.fegin.domain.event.dto.UseCouponResponse;
import com.ed.orderservice.libs.response.ApiResponse;
import java.util.UUID;

public interface CouponUseInPort {

  ApiResponse<UseCouponResponse> useCoupon(UUID couponPublicId, UseCouponRequest useCouponRequest);

  ApiResponse<UseCouponResponse> cancelUseCoupon(UUID couponPublicId);
  
}
