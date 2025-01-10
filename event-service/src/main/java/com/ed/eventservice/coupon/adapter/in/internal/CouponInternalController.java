package com.ed.eventservice.coupon.adapter.in.internal;

import com.ed.eventservice.coupon.adapter.in.internal.dto.UseCouponRequest;
import com.ed.eventservice.coupon.application.port.in.CouponUseCase;
import com.ed.eventservice.coupon.application.port.in.CouponUseCommand;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/internal/coupons")
@RequiredArgsConstructor
public class CouponInternalController {

  private final CouponUseCase couponUseCase;

  @PostMapping("/{couponPublicId}/orderInfo")
  public void useCoupon(
      @PathVariable UUID couponPublicId,
      @RequestBody UseCouponRequest useCouponRequest
  ) {

    couponUseCase.useCoupon(CouponUseCommand.builder()
        .couponId(couponPublicId)
        .userId(useCouponRequest.getUserId())
        .brandId(useCouponRequest.getBrandId())
        .productId(useCouponRequest.getProductId())
        .orderId(useCouponRequest.getOrderId())
        .build());
  }
}
