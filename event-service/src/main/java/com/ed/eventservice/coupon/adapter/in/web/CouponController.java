package com.ed.eventservice.coupon.adapter.in.web;

import com.ed.eventservice.coupon.adapter.in.web.dto.IssueCouponRequest;
import com.ed.eventservice.coupon.application.port.in.CouponUseCase;
import com.ed.eventservice.coupon.application.port.in.IssueCouponCommand;
import com.ed.eventservice.coupon.application.port.out.dto.IssueCouponResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

  private final CouponUseCase couponUseCase;

  @PostMapping("/{couponId}/user")
  public IssueCouponResponse issueCouponToUser(
      @PathVariable UUID couponId,
      @RequestBody IssueCouponRequest request
  ) {

    return couponUseCase.issueCoupon(IssueCouponCommand.builder()
        .couponId(couponId)
        .userId(request.getUserId())
        .build());
  }
}
