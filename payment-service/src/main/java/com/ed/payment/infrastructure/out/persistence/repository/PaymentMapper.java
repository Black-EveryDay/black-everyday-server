package com.ed.payment.infrastructure.out.persistence.repository;

import com.ed.payment.application.port.out.persistence.PaymentResponse;
import com.ed.payment.domain.Payment;
import com.ed.payment.infrastructure.out.persistence.entity.PaymentJpaEntity;
import org.springframework.stereotype.Component;

@Component
class PaymentMapper {

  PaymentResponse mapToApplication(PaymentJpaEntity paymentJpaEntity) {
    return new PaymentResponse(
        paymentJpaEntity.getPaymentPublicId(),
        paymentJpaEntity.getIdempotencyKey(),
        paymentJpaEntity.getOrderPublicId(),
        paymentJpaEntity.getOrderName(),
        paymentJpaEntity.getAmount(),
        paymentJpaEntity.getConfirmDeadline(),
        paymentJpaEntity.getCancelDeadLine());
  }

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
