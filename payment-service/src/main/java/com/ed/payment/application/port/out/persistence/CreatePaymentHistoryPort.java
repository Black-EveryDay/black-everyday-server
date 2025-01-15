package com.ed.payment.application.port.out.persistence;

import com.ed.payment.domain.PaymentStatus;

public interface CreatePaymentHistoryPort {
  void createPaymentHistory(Long paymentId, Long totalAmount, Long balanceAmount);
  void createConfirmSuccessPaymentHistory(Long paymentId, String lastTransactionKey, PaymentStatus paymentStatus, Long totalAmount, Long balanceAmount);
  void createFailPaymentHistory(Long paymentId, PaymentStatus paymentStatus);
  void createCancelSuccessPaymentHistory(Long paymentId, String lastTransactionKey, PaymentStatus paymentStatus, Long totalAmount, Long balanceAmount, Long cancelAmount, String cancelReason);
}
