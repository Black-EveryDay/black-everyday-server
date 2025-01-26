package com.ed.orderservice.infrastructure.external.fegin.domain.coupon;

import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.config.EventFeignErrorDecoder;
import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.dto.UseCouponRequest;
import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.dto.UseCouponResponse;
import com.ed.orderservice.libs.response.ApiResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "coupon-service", configuration = EventFeignErrorDecoder.class)
public interface CouponClient {

  @PostMapping("/api/v1/internal/coupons/{couponPublicId}/orderInfo")
  ApiResponse<UseCouponResponse> useCoupon(
      @PathVariable("couponPublicId") UUID couponPublicId,
      @RequestBody UseCouponRequest useCouponRequest
  );

  @PostMapping("/api/v1/internal/coupons/{couponPublicId}/orderInfo/cancel")
  ApiResponse<UseCouponResponse> cancelUseCoupon(
      @PathVariable UUID couponPublicId
  );
}
