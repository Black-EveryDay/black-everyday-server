package com.ed.payment.application.port.in;

public interface ConfirmPaymentUseCase {
  void confirmPayment(ConfirmPaymentCommand command);
}
