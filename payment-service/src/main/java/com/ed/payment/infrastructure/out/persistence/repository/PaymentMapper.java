package com.ed.payment.infrastructure.out.persistence.repository;

import com.ed.payment.domain.Payment;
import com.ed.payment.infrastructure.out.persistence.entity.PaymentJpaEntity;
import org.springframework.stereotype.Component;

@Component
class PaymentMapper {

  Payment mapToDomain(PaymentJpaEntity paymentJpaEntity) {
    return new Payment(
        paymentJpaEntity.getId(),
        paymentJpaEntity.getPaymentKey(),
        paymentJpaEntity.getIdempotencyKey(),
        paymentJpaEntity.getOrderPublicId(),
        paymentJpaEntity.getOrderName(),
        paymentJpaEntity.getAmount());
  }
}
