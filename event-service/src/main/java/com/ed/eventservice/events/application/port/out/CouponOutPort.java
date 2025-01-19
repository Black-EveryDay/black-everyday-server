package com.ed.eventservice.events.application.port.out;

import com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto.CouponTemplateDetailResponse;
import java.util.List;
import java.util.UUID;

public interface CouponOutPort {

  List<CouponTemplateDetailResponse> getCouponTemplateById(UUID couponTemplateId);
}
