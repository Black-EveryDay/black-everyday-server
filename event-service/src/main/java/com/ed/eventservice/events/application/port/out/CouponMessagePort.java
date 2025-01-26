package com.ed.eventservice.events.application.port.out;

import com.ed.eventservice.events.domain.EventUser;

public interface CouponMessagePort {

  void sendCouponMessage(EventUser eventUser);
}
