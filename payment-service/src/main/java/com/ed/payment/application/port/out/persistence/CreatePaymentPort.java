package com.ed.payment.application.port.out.persistence;

import com.ed.payment.domain.Payment;

public interface CreatePaymentPort {
  Payment initPayment(
      String userPublicId, String orderPublicId, String orderName, int amount);
}
