package com.ed.payment.application.port.out.persistence;

import com.ed.payment.domain.Payment;

public interface ReadPaymentPort {
  Payment findPayment(String orderPublicId);
  boolean existsPayment(String orderPublicId);
}
