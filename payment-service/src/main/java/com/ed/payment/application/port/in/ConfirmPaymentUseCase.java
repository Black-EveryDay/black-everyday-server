package com.ed.payment.application.port.in;

import com.ed.payment.application.port.out.pg.PaymentDone;

public interface ConfirmPaymentUseCase {
  PaymentDone confirmPayment(ConfirmPaymentCommand command);
}
