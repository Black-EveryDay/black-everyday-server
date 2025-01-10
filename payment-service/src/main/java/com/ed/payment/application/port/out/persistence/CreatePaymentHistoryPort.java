package com.ed.payment.application.port.out.persistence;

import com.ed.payment.domain.PaymentStatus;

public interface CreatePaymentHistoryPort {
  void initPaymentHistory(Long paymentId, Long amount);
  void createConfirmSuccessPaymentHistory(Long paymentId, String lastTransactionKey, PaymentStatus paymentStatus, Long totalAmount, Long balanceAmount);
  void createFailPaymentHistory(Long paymentId, PaymentStatus paymentStatus);
}
