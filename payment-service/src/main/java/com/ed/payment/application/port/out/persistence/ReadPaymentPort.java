package com.ed.payment.application.port.out.persistence;

import com.ed.payment.domain.Payment;
import java.util.List;

public interface ReadPaymentPort {
  boolean existsByOrderPublicId(String orderPublicId);
  List<PaymentResponse> getReadyPayments(String userPublicId);
  Payment getPaymentByOrderPublicId(String orderPublicId);
}
