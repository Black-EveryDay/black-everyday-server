package com.ed.eventservice.coupon.adapter.in.web;

import static com.ed.eventservice.libs.common.ApiResponseUtils.created;

import com.ed.eventservice.coupon.adapter.in.web.dto.CreateCouponTemplateRequest;
import com.ed.eventservice.coupon.application.port.in.CouponUseCase;
import com.ed.eventservice.coupon.application.port.out.dto.CreateCouponTemplateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coupon-templates")
@RequiredArgsConstructor
public class CouponTemplateController {

  private final CouponUseCase couponUseCase;

  @PostMapping
  public ResponseEntity<CreateCouponTemplateResponse> createCouponTemplate(
      @Valid @RequestBody CreateCouponTemplateRequest createCouponTemplateRequest) {
    return created(couponUseCase.createCouponTemplate(createCouponTemplateRequest.toCommand()));
  }
}
