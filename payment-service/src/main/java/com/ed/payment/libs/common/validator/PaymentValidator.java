package com.ed.payment.libs.common.validator;

import com.ed.payment.domain.Payment;
import java.time.LocalDateTime;

public interface PaymentValidator {
  void validate(Payment payment, LocalDateTime requestDateTime, Long requestAmount);
}
