package com.ed.payment.application.port.in;

import com.ed.payment.application.port.in.command.PaymentRequestFailCommand;
import com.ed.payment.application.port.out.pg.dtos.PaymentFailResponse;

public interface HandleFailPaymentUseCase {
  PaymentFailResponse handleFailPayment(PaymentRequestFailCommand command);
}
