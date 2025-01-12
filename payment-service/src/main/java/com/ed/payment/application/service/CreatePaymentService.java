package com.ed.payment.application.service;

import com.ed.OrderPaymentCreateRequest;
import com.ed.payment.application.port.in.CreatePaymentUseCase;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.CreatePaymentPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePaymentService implements CreatePaymentUseCase {

  private final ReadPaymentPort readPaymentPort;
  private final CreatePaymentPort createPaymentPort;
  private final CreatePaymentHistoryPort createPaymentHistoryPort;

  @Transactional
  @Override
  public void createPayment(OrderPaymentCreateRequest request) {
    if (readPaymentPort.existsByOrderPublicId(request.getOrderId())) {
      logBadPaymentRequest(request);
      return;
    }

    createPaymentAndPaymentHistory(request);
  }

  private void createPaymentAndPaymentHistory(OrderPaymentCreateRequest request) {
    Payment payment = createPaymentPort.createPayment(
        request.getUserId(),
        request.getOrderId(),
        request.getOrderName(),
        request.getTotalAmount(),
        request.getPaymentDeadline(),
        request.getOrderCancelDeadline());

    createPaymentHistoryPort.createPaymentHistory(
        payment.getPaymentId(),
        payment.getAmount());
  }

  private void logBadPaymentRequest(OrderPaymentCreateRequest request) {
    log.info("Bad Request = payment based on userId: {}, orderId: {} is already exist",
        request.getUserId(), request.getOrderId());
  }
}
