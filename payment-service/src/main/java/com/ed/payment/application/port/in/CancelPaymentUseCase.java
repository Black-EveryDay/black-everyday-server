package com.ed.payment.application.port.in;

import com.ed.OrderPaymentCancelRequestEvent;

public interface CancelPaymentUseCase {
  void cancelPayment(OrderPaymentCancelRequestEvent request);
}
