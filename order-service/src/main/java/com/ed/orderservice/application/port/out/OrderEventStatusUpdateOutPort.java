package com.ed.orderservice.application.port.out;

public interface OrderEventStatusUpdateOutPort {
  void updateToPaymentSuccess(String orderId);
  void updateToPaymentFailure(String orderId);
  void updateToPaymentCanceled(String orderId);
  void updateToPaymentCancelFailed(String orderId);

}

