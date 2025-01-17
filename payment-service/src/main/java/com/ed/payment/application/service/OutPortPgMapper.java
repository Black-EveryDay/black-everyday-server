package com.ed.payment.application.service;

import com.ed.OrderPaymentCancelRequestEvent;
import com.ed.payment.application.port.out.pg.dtos.CancelPaymentRequest;
import com.ed.payment.domain.Payment;
import org.springframework.stereotype.Component;

@Component
class OutPortPgMapper {

  CancelPaymentRequest cancelPaymentToPg(Payment payment, OrderPaymentCancelRequestEvent request) {
    return CancelPaymentRequest.builder()
        .paymentKey(payment.getPaymentKey())
        .idempotencyKey(payment.getIdempotencyKey())
        .cancelReason(request.getCancelReason())
        .cancelAmount(request.getCancelAmount())
        .build();
  }
}
