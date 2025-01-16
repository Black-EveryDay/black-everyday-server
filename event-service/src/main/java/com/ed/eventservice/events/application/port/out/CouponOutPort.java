package com.ed.eventservice.events.application.port.out;

import java.util.UUID;

public interface CouponOutPort {

  void getCouponTemplateById(UUID couponTemplateId);
}
