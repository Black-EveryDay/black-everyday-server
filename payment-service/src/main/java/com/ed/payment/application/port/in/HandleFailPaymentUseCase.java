package com.ed.payment.application.port.in;

import com.ed.payment.application.port.in.command.HandleFailPaymentCommand;
import com.ed.payment.application.port.out.pg.dtos.PaymentFailResponse;

public interface HandleFailPaymentUseCase {
  PaymentFailResponse handleFailPayment(HandleFailPaymentCommand command);
}
