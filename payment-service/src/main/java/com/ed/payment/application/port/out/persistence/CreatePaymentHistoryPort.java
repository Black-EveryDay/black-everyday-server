package com.ed.payment.application.port.out.persistence;

import com.ed.payment.application.port.out.persistence.dtos.CreateCancelPaymentHistoryRequest;
import com.ed.payment.application.port.out.persistence.dtos.CreateConfirmPaymentHistoryRequest;

public interface CreatePaymentHistoryPort {
  void createPaymentHistory(Long paymentId, Long amount);
  void createConfirmPaymentHistory(CreateConfirmPaymentHistoryRequest request);
  void createFailPaymentHistory(Long paymentId);
  void createCancelPaymentHistory(CreateCancelPaymentHistoryRequest request);
}
