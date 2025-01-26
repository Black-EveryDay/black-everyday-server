package com.ed.payment.infrastructure.out.persistence.repository;

import com.ed.payment.application.port.out.persistence.dtos.CreatePaymentRequest;
import com.ed.payment.application.port.out.persistence.dtos.PaymentResponse;
import com.ed.payment.domain.Payment;
import com.ed.payment.infrastructure.out.persistence.entity.PaymentJpaEntity;
import org.springframework.stereotype.Component;

@Component
class PaymentPersistenceMapper {

  PaymentJpaEntity createRequestToJpaEntity(CreatePaymentRequest request) {
    PaymentJpaEntity paymentJpaEntity = PaymentJpaEntity.createPayment()
        .userPublicId(request.getUserPublicId())
        .orderPublicId(request.getOrderPublicId())
        .orderName(request.getOrderName())
        .amount(request.getAmount())
        .confirmDeadline(request.getConfirmDeadline())
        .cancelDeadLine(request.getCancelDeadLine())
        .build();

    paymentJpaEntity.addPaymentHistoryJpaEntity();

    return paymentJpaEntity;
  }

  PaymentResponse mapToPaymentResponse(PaymentJpaEntity entity) {
    return PaymentResponse.builder()
        .paymentPublicId(entity.getUserPublicId())
        .idempotencyKey(entity.getIdempotencyKey())
        .orderPublicId(entity.getOrderPublicId())
        .orderName(entity.getOrderName())
        .amount(entity.getTotalAmount())
        .confirmDeadline(entity.getConfirmDeadline())
        .cancelDeadLine(entity.getCancelDeadLine())
        .build();
  }

  Payment mapToDomain(PaymentJpaEntity entity) {
    return Payment.builder()
        .paymentId(entity.getId())
        .paymentPublicId(entity.getPaymentPublicId())
        .paymentKey(entity.getPaymentKey())
        .idempotencyKey(entity.getIdempotencyKey())
        .userId(entity.getUserPublicId())
        .paymentStatus(entity.getPaymentStatus())
        .orderPublicId(entity.getOrderPublicId())
        .orderName(entity.getOrderName())
        .totalAmount(entity.getTotalAmount())
        .balanceAmount(entity.getBalanceAmount())
        .confirmDeadline(entity.getConfirmDeadline())
        .cancelDeadLine(entity.getCancelDeadLine())
        .build();
  }
}
