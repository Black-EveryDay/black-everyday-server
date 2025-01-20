package com.ed.payment.application.port.out.persistence;

import com.ed.payment.application.port.out.persistence.dtos.UpdateCancelPaymentRequest;
import com.ed.payment.domain.PaymentStatus;

public interface UpdatePaymentPort {
  void updatePaymentStatusAbortedById(Long paymentId);
  void updatePaymentStatusAndPaymentKeyById(Long paymentId, PaymentStatus paymentStatus, String paymentKey);
  void updatePaymentStatusAndIdempotencyKeyById(UpdateCancelPaymentRequest request);
}
