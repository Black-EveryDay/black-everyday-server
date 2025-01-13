package com.ed.orderservice.infrastructure.external.fegin.domain.event;

import com.ed.orderservice.infrastructure.external.fegin.config.FeignClientConfig;
import com.ed.orderservice.infrastructure.external.fegin.domain.event.dto.UseCouponRequest;
import com.ed.orderservice.infrastructure.external.fegin.domain.event.dto.UseCouponResponse;
import com.ed.orderservice.libs.response.ApiResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "event-service", configuration = FeignClientConfig.class)
public interface CouponClient {

  @PostMapping("/api/v1/internal/coupons/{couponPublicId}/orderInfo")
  ApiResponse<UseCouponResponse> useCoupon(
      @PathVariable("couponPublicId") UUID couponPublicId,
      @RequestBody UseCouponRequest useCouponRequest
  );

  @PostMapping("/api/v1/internal/coupons/{couponPublicId}/orderInfo/cancel")
  UseCouponResponse cancelUseCoupon(
      @PathVariable UUID couponPublicId
  );
}
