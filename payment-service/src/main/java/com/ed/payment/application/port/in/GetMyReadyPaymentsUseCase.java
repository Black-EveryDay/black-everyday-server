package com.ed.payment.application.port.in;

import com.ed.payment.application.port.out.persistence.PaymentResponse;
import java.util.List;

public interface GetMyReadyPaymentsUseCase {
  List<PaymentResponse> getMyReadyPayments(String userPublicId);
}
