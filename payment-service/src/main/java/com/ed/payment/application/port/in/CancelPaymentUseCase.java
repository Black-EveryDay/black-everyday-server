package com.ed.payment.application.port.in;

import com.ed.OrderPaymentCancelRequest;

public interface CancelPaymentUseCase {
  void cancelPayment(OrderPaymentCancelRequest request);
}
