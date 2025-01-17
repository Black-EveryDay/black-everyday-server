package com.ed.payment.application.service;

import com.ed.OrderPaymentCreateRequestEvent;
import com.ed.payment.application.port.out.persistence.dtos.CreateCancelPaymentHistoryRequest;
import com.ed.payment.application.port.out.persistence.dtos.CreateConfirmPaymentHistoryRequest;
import com.ed.payment.application.port.out.persistence.dtos.CreatePaymentRequest;
import com.ed.payment.application.port.out.persistence.dtos.UpdateCancelPaymentRequest;
import com.ed.payment.application.port.out.pg.dtos.PaymentCanceledResponse;
import com.ed.payment.application.port.out.pg.dtos.PaymentDoneResponse;
import org.springframework.stereotype.Component;

@Component
class OutPortPersistenceMapper {

  CreatePaymentRequest createPaymentToPersistence(
      OrderPaymentCreateRequestEvent request) {
    return CreatePaymentRequest.builder()
        .userPublicId(request.getUserId())
        .orderPublicId(request.getOrderId())
        .orderName(request.getOrderName())
        .amount(request.getTotalAmount())
        .confirmDeadline(request.getPaymentDeadline())
        .cancelDeadLine(request.getOrderCancelDeadline())
        .build();
  }

  UpdateCancelPaymentRequest updateCancelPaymentToPersistence(Long paymentId, PaymentCanceledResponse response) {
    return UpdateCancelPaymentRequest.builder()
        .paymentId(paymentId)
        .paymentStatus(response.getPaymentStatus())
        .balanceAmount(response.getBalanceAmount())
        .build();
  }

  CreateConfirmPaymentHistoryRequest confirmHistoryToPersistence(Long paymentId, PaymentDoneResponse response) {
    return CreateConfirmPaymentHistoryRequest.builder()
        .paymentId(paymentId)
        .lastTransactionKey(response.getLastTransactionKey())
        .paymentStatus(response.getPaymentStatus())
        .totalAmount(response.getTotalAmount())
        .balanceAmount(response.getBalanceAmount())
        .build();
  }

  CreateCancelPaymentHistoryRequest cancelHistoryToPersistence(Long paymentId, PaymentCanceledResponse response) {
    return CreateCancelPaymentHistoryRequest.builder()
        .paymentId(paymentId)
        .lastTransactionKey(response.getLastTransactionKey())
        .paymentStatus(response.getPaymentStatus())
        .totalAmount(response.getTotalAmount())
        .balanceAmount(response.getBalanceAmount())
        .cancelAmount(response.getCancelAmount())
        .cancelReason(response.getCancelReason())
        .build();
  }
}
