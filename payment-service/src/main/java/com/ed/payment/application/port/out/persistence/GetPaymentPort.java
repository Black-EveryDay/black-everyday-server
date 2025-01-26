package com.ed.payment.application.port.out.persistence;

import com.ed.payment.application.port.out.persistence.dtos.PaymentResponse;
import com.ed.payment.application.port.out.persistence.dtos.SettleablePaymentResponse;
import com.ed.payment.domain.Payment;
import java.time.LocalDateTime;
import java.util.List;

public interface GetPaymentPort {
  boolean existsByOrderPublicId(String orderPublicId);
  List<PaymentResponse> getReadyPayments(String userPublicId);
  Payment getPaymentByOrderPublicId(String orderPublicId);
  List<SettleablePaymentResponse> getSettleablePayments(LocalDateTime requestDateTime, Long currentId, int pageSize);
}
