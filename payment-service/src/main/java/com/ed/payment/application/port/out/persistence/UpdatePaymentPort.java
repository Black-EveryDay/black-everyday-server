package com.ed.payment.application.port.out.persistence;

import com.ed.payment.infrastructure.out.persistence.PaymentStatus;

public interface UpdatePaymentPort {
  void updatePaymentStatus(Long paymentId, PaymentStatus paymentStatus);
  void updatePaymentAfterVerifying(Long paymentId, String paymentKey, PaymentStatus paymentStatus);
}
