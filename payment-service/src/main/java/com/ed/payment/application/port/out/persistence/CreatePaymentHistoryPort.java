package com.ed.payment.application.port.out.persistence;

import com.ed.payment.infrastructure.out.persistence.PaymentStatus;

public interface CreatePaymentHistoryPort {
  void initPaymentHistory(Long paymentId, int amount);
  void createPaymentHistory(Long paymentId, int amount, PaymentStatus paymentStatus);
}
