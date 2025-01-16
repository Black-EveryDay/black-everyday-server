package com.ed.payment.application.port.in;

import com.ed.payment.application.port.in.command.ConfirmPaymentCommand;
import com.ed.payment.application.port.out.pg.dtos.PaymentDoneResponse;

public interface ConfirmPaymentUseCase {
  PaymentDoneResponse confirmPayment(ConfirmPaymentCommand command);
}
