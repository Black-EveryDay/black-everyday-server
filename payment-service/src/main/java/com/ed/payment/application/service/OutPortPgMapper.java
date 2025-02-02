package com.ed.payment.application.service;

import com.ed.payment.application.port.out.pg.dtos.CancelPaymentRequest;
import com.ed.payment.application.port.out.pg.dtos.ConfirmPaymentRequest;
import org.springframework.stereotype.Component;

@Component
class OutPortPgMapper {

  ConfirmPaymentRequest toConfirmRequest(String paymentKey, String orderPublicId, Long totalAmount) {
    return ConfirmPaymentRequest.builder()
        .paymentKey(paymentKey)
        .orderId(orderPublicId)
        .amount(totalAmount)
        .build();
  }

  CancelPaymentRequest toCancelRequest(String cancelReason, long cancelAmount) {
    return CancelPaymentRequest.builder()
        .cancelReason(cancelReason)
        .cancelAmount(cancelAmount)
        .build();
  }
}
