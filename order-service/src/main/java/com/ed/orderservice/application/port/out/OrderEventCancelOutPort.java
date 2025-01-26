package com.ed.orderservice.application.port.out;

import com.ed.orderservice.domain.vo.order.OrderEvent;

  public interface OrderEventCancelOutPort {

    OrderEvent save(byte[] playLoad, String orderPublicId);
  }
