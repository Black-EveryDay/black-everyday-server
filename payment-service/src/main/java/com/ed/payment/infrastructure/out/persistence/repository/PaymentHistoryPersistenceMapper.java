package com.ed.payment.infrastructure.out.persistence.repository;

import com.ed.payment.application.port.out.persistence.dtos.CreateCancelPaymentHistoryRequest;
import com.ed.payment.application.port.out.persistence.dtos.CreateConfirmPaymentHistoryRequest;
import com.ed.payment.infrastructure.out.persistence.entity.PaymentHistoryJpaEntity;
import org.springframework.stereotype.Component;

@Component
class PaymentHistoryPersistenceMapper {

  PaymentHistoryJpaEntity createRequestToJpaEntity(Long paymentId, Long amount) {
    return PaymentHistoryJpaEntity.createPaymentHistory()
        .paymentId(paymentId)
        .amount(amount)
        .build();
  }

  PaymentHistoryJpaEntity confirmRequestToJpaEntity(
      CreateConfirmPaymentHistoryRequest request) {
    return PaymentHistoryJpaEntity.createConfirmPaymentHistory()
        .paymentId(request.getPaymentId())
        .lastTransactionKey(request.getLastTransactionKey())
        .paymentStatus(request.getPaymentStatus())
        .totalAmount(request.getTotalAmount())
        .balanceAmount(request.getBalanceAmount())
        .build();
  }

  PaymentHistoryJpaEntity failRequestToJpaEntity(Long paymentId) {
    return PaymentHistoryJpaEntity.createFailPaymentHistory()
        .paymentId(paymentId)
        .build();
  }

  PaymentHistoryJpaEntity cancelRequestToJpaEntity(
      CreateCancelPaymentHistoryRequest request) {
    return PaymentHistoryJpaEntity.createCancelPaymentHistory()
        .paymentId(request.getPaymentId())
        .lastTransactionKey(request.getLastTransactionKey())
        .paymentStatus(request.getPaymentStatus())
        .totalAmount(request.getTotalAmount())
        .balanceAmount(request.getBalanceAmount())
        .cancelAmount(request.getCancelAmount())
        .cancelReason(request.getCancelReason())
        .build();
  }
}
