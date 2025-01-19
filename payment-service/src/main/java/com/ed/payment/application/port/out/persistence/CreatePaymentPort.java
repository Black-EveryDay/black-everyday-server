package com.ed.payment.application.port.out.persistence;

import com.ed.payment.application.port.out.persistence.dtos.CreatePaymentRequest;
import com.ed.payment.domain.Payment;

public interface CreatePaymentPort {
  Payment createPayment(CreatePaymentRequest request);
}
