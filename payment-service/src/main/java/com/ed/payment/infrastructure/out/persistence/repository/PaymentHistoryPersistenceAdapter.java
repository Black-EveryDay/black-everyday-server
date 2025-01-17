package com.ed.payment.infrastructure.out.persistence.repository;

import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.dtos.CreateCancelPaymentHistoryRequest;
import com.ed.payment.application.port.out.persistence.dtos.CreateConfirmPaymentHistoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class PaymentHistoryPersistenceAdapter implements CreatePaymentHistoryPort {

  private final PaymentHistoryPersistenceMapper paymentHistoryPersistenceMapper;
  private final SpringDataPaymentHistoryRepository paymentHistoryRepository;

  @Override
  public void createPaymentHistory(Long paymentId, Long amount) {
    paymentHistoryRepository.save(
        paymentHistoryPersistenceMapper.createRequestToJpaEntity(paymentId, amount));
  }

  @Override
  public void createConfirmPaymentHistory(
      CreateConfirmPaymentHistoryRequest request) {
    paymentHistoryRepository.save(
        paymentHistoryPersistenceMapper.confirmRequestToJpaEntity(request));
  }

  @Override
  public void createFailPaymentHistory(Long paymentId) {
    paymentHistoryRepository.save(
        paymentHistoryPersistenceMapper.failRequestToJpaEntity(paymentId));
  }

  @Override
  public void createCancelPaymentHistory(
      CreateCancelPaymentHistoryRequest request) {
    paymentHistoryRepository.save(
        paymentHistoryPersistenceMapper.cancelRequestToJpaEntity(request));
  }
}
