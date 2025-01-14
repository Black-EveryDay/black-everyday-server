package com.ed.payment.application.port.out.pg;

public interface CancelPaymentPort {
  PaymentCanceled cancelPayment(String paymentKey, String idempotencyKey, String cancelReason, Long cancelAmount);
}
