package com.ed.payment.application.port.out.persistence;

import com.ed.payment.domain.PaymentStatus;

public interface UpdatePaymentPort {
  void updatePaymentStatusById(Long paymentId, PaymentStatus paymentStatus);
  void updatePaymentStatusAndPaymentKeyById(Long paymentId, PaymentStatus paymentStatus, String paymentKey);
}
