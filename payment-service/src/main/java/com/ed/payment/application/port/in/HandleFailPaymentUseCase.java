package com.ed.payment.application.port.in;

public interface HandleFailPaymentUseCase {
  void handleFailPayment(HandleFailPaymentCommand command);
}
