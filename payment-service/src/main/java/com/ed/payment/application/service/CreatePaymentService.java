package com.ed.payment.application.service;

import com.ed.OrderPaymentCreateRequestEvent;
import com.ed.payment.application.port.in.CreatePaymentUseCase;
import com.ed.payment.application.port.out.persistence.CreatePaymentPort;
import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePaymentService implements CreatePaymentUseCase {

  private final OutPortPersistenceMapper outPortPersistenceMapper;
  private final GetPaymentPort getPaymentPort;
  private final CreatePaymentPort createPaymentPort;

  @Transactional
  @Override
  public void createPayment(OrderPaymentCreateRequestEvent request) {
    if (getPaymentPort.existsByOrderPublicId(request.getOrderId())) {
      logBadPaymentRequest(request);
      return;
    }

    createPaymentPort.createPayment(outPortPersistenceMapper.createPaymentToPersistence(request));
  }

  private void logBadPaymentRequest(OrderPaymentCreateRequestEvent request) {
    log.info("Bad Request = payment based on userId: {}, orderId: {} is already exist",
        request.getUserId(), request.getOrderId());
  }
}
