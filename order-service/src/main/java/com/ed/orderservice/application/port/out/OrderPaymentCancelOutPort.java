package com.ed.orderservice.application.port.out;

import com.ed.OrderPaymentCancelRequestEvent;

public interface OrderPaymentCancelOutPort {
  void sendPaymentCancelRequest(OrderPaymentCancelRequestEvent request);
}
