package com.ed.orderservice.application.port.out;

import com.ed.orderservice.domain.vo.order.OrderEvent;

public interface OrderEventCreatedOutPort {

  OrderEvent save(byte[] playLoad, String orderPublicId);
}
