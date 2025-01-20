package com.ed.eventservice.events.adapter.out.feign.domain.coupon.adapter;

import com.ed.eventservice.events.adapter.out.feign.domain.coupon.CouponClient;
import com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto.CouponTemplateDetailResponse;
import com.ed.eventservice.events.application.port.out.CouponOutPort;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponUseAdapter implements CouponOutPort {

  private final CouponClient couponClient;

  @Override
  public List<CouponTemplateDetailResponse> getCouponTemplateById(UUID couponTemplateId) {
    return couponClient.searchCouponTemplates(couponTemplateId)
        .getBody()
        .getContent();
  }
}
