package com.ed.payment.infrastructure.out.pg.toss;

import static com.ed.payment.domain.PaymentStatus.getCancelStatus;
import static com.ed.payment.domain.PaymentStatus.getConfirmStatus;

import com.ed.payment.application.port.out.pg.dtos.PaymentCanceledResponse;
import com.ed.payment.application.port.out.pg.dtos.PaymentDoneResponse;
import com.ed.payment.infrastructure.out.pg.toss.dtos.TossPaymentCanceledResponse;
import com.ed.payment.infrastructure.out.pg.toss.dtos.TossPaymentDoneResponse;
import org.springframework.stereotype.Component;

@Component
class PaymentPgMapper {

  PaymentDoneResponse confirmResponseToApplication(
      TossPaymentDoneResponse response) {
    return PaymentDoneResponse.builder()
        .paymentKey(response.getPaymentKey())
        .orderId(response.getOrderId())
        .totalAmount(response.getTotalAmount())
        .balanceAmount(response.getBalanceAmount())
        .paymentStatus(getConfirmStatus(response.getStatus()))
        .lastTransactionKey(response.getLastTransactionKey())
        .build();
  }

  PaymentCanceledResponse cancelResponseToApplication(
      TossPaymentCanceledResponse response) {
    return PaymentCanceledResponse.builder()
        .paymentKey(response.getPaymentKey())
        .orderId(response.getOrderId())
        .totalAmount(response.getTotalAmount())
        .balanceAmount(response.getBalanceAmount())
        .cancelAmount(response.getCancels().getLast().getCancelAmount())
        .cancelReason(response.getCancels().getLast().getCancelReason())
        .paymentStatus(getCancelStatus(response.getStatus()))
        .lastTransactionKey(response.getLastTransactionKey())
        .build();
  }
}
