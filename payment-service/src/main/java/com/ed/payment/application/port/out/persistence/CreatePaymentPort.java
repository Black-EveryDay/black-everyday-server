package com.ed.payment.application.port.out.persistence;

import com.ed.payment.domain.Payment;
import java.time.LocalDateTime;

public interface CreatePaymentPort {
  Payment createPayment(
      String userPublicId, String orderPublicId, String orderName, Long amount,
      LocalDateTime confirmDeadline, LocalDateTime cancelDeadLine);
}
