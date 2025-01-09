package com.ed.eventservice.coupon.adapter.in.web;

import static com.ed.eventservice.libs.common.ApiResponseUtils.created;

import com.ed.eventservice.coupon.adapter.in.web.dto.CreateCouponRequest;
import com.ed.eventservice.coupon.adapter.in.web.dto.CreateCouponTemplateRequest;
import com.ed.eventservice.coupon.application.port.in.CouponTemplateUseCase;
import com.ed.eventservice.coupon.application.port.in.CreateCouponCommand;
import com.ed.eventservice.coupon.application.port.out.dto.CreateCouponTemplateResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coupon-templates")
@RequiredArgsConstructor
public class CouponTemplateController {

  private final CouponTemplateUseCase couponTemplateUseCase;

  @PostMapping
  public ResponseEntity<CreateCouponTemplateResponse> createCouponTemplate(
      @Valid @RequestBody CreateCouponTemplateRequest createCouponTemplateRequest) {
    return created(
        couponTemplateUseCase.createCouponTemplate(createCouponTemplateRequest.toCommand()));
  }

  @PostMapping("{couponTemplateId}/coupons")
  public ResponseEntity<String> createCoupon(@PathVariable UUID couponTemplateId,
      @Valid @RequestBody CreateCouponRequest createCouponRequest) {
    couponTemplateUseCase.createCoupon(CreateCouponCommand.builder()
        .couponTemplateId(couponTemplateId)
        .quantity(createCouponRequest.getQuantity())
        .build());
    return created(null);
  }
}
