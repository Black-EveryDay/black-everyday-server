package com.ed.payment.application.port.in;

import com.ed.payment.application.port.out.pg.PaymentFail;

public interface HandleFailPaymentUseCase {
  PaymentFail handleFailPayment(HandleFailPaymentCommand command);
}
