package com.ed.eventservice.coupon.adapter.in.web;

import com.ed.eventservice.coupon.adapter.in.web.dto.CreateCouponTemplateRequest;
import com.ed.eventservice.coupon.application.port.in.CouponUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

  private final CouponUseCase couponUseCase;

  @PostMapping("/templates")
  public void createCouponTemplate(
      @Valid @RequestBody CreateCouponTemplateRequest createCouponTemplateRequest) {
    couponUseCase.createCouponTemplate(createCouponTemplateRequest.toCommand());
  }
}
