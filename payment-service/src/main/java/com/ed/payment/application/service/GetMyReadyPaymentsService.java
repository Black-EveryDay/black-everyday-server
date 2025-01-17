package com.ed.payment.application.service;

import com.ed.payment.application.port.in.GetMyReadyPaymentsUseCase;
import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.dtos.PaymentResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetMyReadyPaymentsService implements GetMyReadyPaymentsUseCase {

  private final GetPaymentPort getPaymentPort;

  @Transactional(readOnly = true)
  @Override
  public List<PaymentResponse> getMyReadyPayments(String userPublicId) {
    return getPaymentPort.getReadyPayments(userPublicId);
  }
}
