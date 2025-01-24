package com.ed.orderservice.application.port.out;

public interface OrderStatusUpdateOutPort {
  void updateToPaymentRequest(String orderId);
  void updateToPaymentWaiting(String orderId);
  void updateToPaymentFailed(String orderId);
  void updateToPaymentCanceledRequest(String orderId);
  void updateToPaymentCanceled(String orderId);
  void updateToPaymentCancelFailed(String orderId);
}
