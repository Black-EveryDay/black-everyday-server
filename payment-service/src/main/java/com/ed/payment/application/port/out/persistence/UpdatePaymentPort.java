package com.ed.payment.application.port.out.persistence;

import com.ed.payment.application.port.out.persistence.dtos.UpdateCancelPaymentRequest;
import com.ed.payment.domain.PaymentStatus;
import java.util.List;

public interface UpdatePaymentPort {
  void updatePaymentStatusAbortedById(Long paymentId);
  void bulkUpdatePaymentStatusByIds(List<Long> paymentIds);
  void updatePaymentStatusAndPaymentKeyById(Long paymentId, PaymentStatus paymentStatus, String paymentKey);
  void updatePaymentStatusAndIdempotencyKeyById(UpdateCancelPaymentRequest request);
}
