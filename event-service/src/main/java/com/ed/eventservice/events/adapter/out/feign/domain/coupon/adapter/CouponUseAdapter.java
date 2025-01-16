package com.ed.eventservice.events.adapter.out.feign.domain.coupon.adapter;

import com.ed.eventservice.events.adapter.out.feign.domain.coupon.CouponClient;
import com.ed.eventservice.events.application.port.out.CouponOutPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponUseAdapter implements CouponOutPort {

  private final CouponClient couponClient;

  @Override
  public void getCouponTemplateById(UUID couponTemplateId) {

  }
}
