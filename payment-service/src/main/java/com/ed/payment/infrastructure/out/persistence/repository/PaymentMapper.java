package com.ed.payment.infrastructure.out.persistence.repository;

import com.ed.payment.domain.Payment;
import com.ed.payment.infrastructure.out.persistence.entity.PaymentJpaEntity;
import org.springframework.stereotype.Component;

@Component
class PaymentMapper {

  Payment mapToDomain(PaymentJpaEntity paymentJpaEntity) {
    return new Payment(
        paymentJpaEntity.getId(),
        paymentJpaEntity.getPaymentPublicId(),
        paymentJpaEntity.getPaymentKey(),
        paymentJpaEntity.getIdempotencyKey(),
        paymentJpaEntity.getUserPublicId(),
        paymentJpaEntity.getPaymentStatus(),
        paymentJpaEntity.getOrderPublicId(),
        paymentJpaEntity.getOrderName(),
        paymentJpaEntity.getAmount(),
        paymentJpaEntity.getConfirmDeadline(),
        paymentJpaEntity.getCancelDeadLine());
  }
}
