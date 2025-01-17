package com.ed.payment.application.port.in;

import com.ed.OrderPaymentCreateRequestEvent;

public interface CreatePaymentUseCase {
  void createPayment(OrderPaymentCreateRequestEvent request);
}
