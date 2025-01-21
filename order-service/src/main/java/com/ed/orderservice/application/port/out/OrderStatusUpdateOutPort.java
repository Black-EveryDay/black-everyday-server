package com.ed.orderservice.application.port.out;

import com.ed.orderservice.domain.vo.order.Order;

public interface OrderStatusUpdateOutPort {
  void updateToPaymentRequest(String orderId);
  void updateToPaymentWaiting(String orderId);
  void updateToPaymentFailed(String orderId);
}
