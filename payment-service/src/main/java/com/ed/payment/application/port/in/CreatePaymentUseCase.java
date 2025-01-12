package com.ed.payment.application.port.in;

import com.ed.OrderPaymentCreateRequest;

public interface CreatePaymentUseCase {
  void createPayment(OrderPaymentCreateRequest request);
}
