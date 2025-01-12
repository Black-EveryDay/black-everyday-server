package com.ed.payment.application.service;

import com.ed.payment.application.port.in.GetMyReadyPaymentsUseCase;
import com.ed.payment.application.port.out.persistence.PaymentResponse;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetMyReadyPaymentsService implements GetMyReadyPaymentsUseCase {

  private final ReadPaymentPort readPaymentPort;

  @Transactional(readOnly = true)
  @Override
  public List<PaymentResponse> getMyReadyPayments(String userPublicId) {
    return readPaymentPort.getReadyPayments(userPublicId);
  }
}
