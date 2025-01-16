package com.ed.payment.application.port.out.pg;

import com.ed.payment.application.port.out.pg.dtos.PaymentDoneResponse;
import com.ed.payment.domain.Payment;

public interface ConfirmPaymentPort {
  PaymentDoneResponse confirmPayment(Payment payment, String paymentKey);
}
