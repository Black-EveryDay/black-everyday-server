package com.ed.payment.infrastructure.out.pg.toss;

import static com.ed.payment.domain.PaymentStatus.ABORTED;
import static com.ed.payment.domain.PaymentStatus.CANCELED;
import static com.ed.payment.domain.PaymentStatus.DONE;

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
        DONE.name().equalsIgnoreCase(tossPaymentDone.getStatus()) ? DONE : ABORTED,
        tossPaymentDone.getLastTransactionKey());
  }

  PaymentCanceled mapCancelResponseToApplication(TossPaymentCanceled tossPaymentCanceled) {
    return PaymentCanceled.of(
        tossPaymentCanceled.getPaymentKey(),
        tossPaymentCanceled.getOrderId(),
        tossPaymentCanceled.getTotalAmount(),
        tossPaymentCanceled.getBalanceAmount(),
        tossPaymentCanceled.getCancels().getFirst().getCancelAmount(),
        tossPaymentCanceled.getCancels().getFirst().getCancelReason(),
        CANCELED.name().equalsIgnoreCase(tossPaymentCanceled.getStatus()) ? CANCELED : ABORTED,
        tossPaymentCanceled.getLastTransactionKey());
  }
}
