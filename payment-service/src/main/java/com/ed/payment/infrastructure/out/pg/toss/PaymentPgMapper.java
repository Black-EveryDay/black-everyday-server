package com.ed.payment.infrastructure.out.pg.toss;

import static com.ed.payment.domain.PaymentStatus.getCancelStatus;
import static com.ed.payment.domain.PaymentStatus.getConfirmStatus;

import com.ed.payment.application.port.out.pg.PaymentCanceled;
import com.ed.payment.application.port.out.pg.PaymentDone;
import org.springframework.stereotype.Component;

@Component
class PaymentPgMapper {

  PaymentDone mapConfirmResponseToApplication(TossPaymentDone tossPaymentDone) {
    return PaymentDone.of(
        tossPaymentDone.getPaymentKey(),
        tossPaymentDone.getOrderId(),
        tossPaymentDone.getTotalAmount(),
        tossPaymentDone.getBalanceAmount(),
        getConfirmStatus(tossPaymentDone.getStatus()),
        tossPaymentDone.getLastTransactionKey());
  }

  PaymentCanceled mapCancelResponseToApplication(
      TossPaymentCanceled tossPaymentCanceled) {
    return PaymentCanceled.of(
        tossPaymentCanceled.getPaymentKey(),
        tossPaymentCanceled.getOrderId(),
        tossPaymentCanceled.getTotalAmount(),
        tossPaymentCanceled.getBalanceAmount(),
        tossPaymentCanceled.getCancels().getLast().getCancelAmount(),
        tossPaymentCanceled.getCancels().getLast().getCancelReason(),
        getCancelStatus(tossPaymentCanceled.getStatus()),
        tossPaymentCanceled.getLastTransactionKey());
  }
}
