package com.ed.payment.application.port.out.pg;

import com.ed.payment.domain.Payment;

public interface ConfirmPaymentPort {
  PaymentDone confirmPayment(Payment payment, String paymentKey);
}
