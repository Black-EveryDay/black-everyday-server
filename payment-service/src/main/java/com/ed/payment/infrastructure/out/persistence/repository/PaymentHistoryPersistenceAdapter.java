package com.ed.payment.infrastructure.out.persistence.repository;

import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.infrastructure.out.persistence.entity.PaymentHistoryJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class PaymentHistoryPersistenceAdapter implements CreatePaymentHistoryPort {

  private final SpringDataPaymentHistoryRepository paymentHistoryRepository;

  @Override
  public void createPaymentHistory(Long paymentId, Long amount) {
    paymentHistoryRepository.save(
        PaymentHistoryJpaEntity.createPaymentHistory(paymentId, amount));
  }

  @Override
  public void createConfirmSuccessPaymentHistory(
      Long paymentId, String lastTransactionKey, PaymentStatus paymentStatus,
      Long totalAmount, Long balanceAmount) {
    paymentHistoryRepository.save(
        PaymentHistoryJpaEntity.createConfirmSuccessPaymentHistory(
            paymentId, lastTransactionKey, paymentStatus, totalAmount, balanceAmount));
  }

  @Override
  public void createFailPaymentHistory(
      Long paymentId, PaymentStatus paymentStatus) {
    paymentHistoryRepository.save(
        PaymentHistoryJpaEntity.createFailPaymentHistory(paymentId, paymentStatus));
  }
}
