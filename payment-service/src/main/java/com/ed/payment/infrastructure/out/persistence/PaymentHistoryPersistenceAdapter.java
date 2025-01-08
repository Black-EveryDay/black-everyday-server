package com.ed.payment.infrastructure.out.persistence;

import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class PaymentHistoryPersistenceAdapter implements CreatePaymentHistoryPort {

  private final SpringDataPaymentHistoryRepository paymentHistoryRepository;

  @Override
  public void initPaymentHistory(Long paymentId, int amount) {
    paymentHistoryRepository.save(
        PaymentHistoryJpaEntity.initPaymentHistory(paymentId, amount));
  }

  @Override
  public void createPaymentHistory(
      Long paymentId, int amount, PaymentStatus paymentStatus) {
    paymentHistoryRepository.save(
        PaymentHistoryJpaEntity.createPaymentHistory(
            paymentId, amount, paymentStatus));
  }
}
