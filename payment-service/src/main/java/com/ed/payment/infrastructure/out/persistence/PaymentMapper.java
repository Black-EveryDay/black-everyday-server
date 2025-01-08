package com.ed.payment.infrastructure.out.persistence;

import com.ed.payment.domain.Payment;
import org.springframework.stereotype.Component;

@Component
class PaymentMapper {

  Payment mapToDomain(PaymentJpaEntity paymentJpaEntity) {
    return new Payment(
        paymentJpaEntity.getId(),
        paymentJpaEntity.getPaymentKey(),
        paymentJpaEntity.getOrderPublicId(),
        paymentJpaEntity.getOrderName(),
        paymentJpaEntity.getAmount());
  }
}
