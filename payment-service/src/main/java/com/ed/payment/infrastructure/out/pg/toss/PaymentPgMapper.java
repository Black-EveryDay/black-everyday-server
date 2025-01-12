package com.ed.payment.infrastructure.out.pg.toss;

import static com.ed.payment.domain.PaymentStatus.ABORTED;
import static com.ed.payment.domain.PaymentStatus.DONE;

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
}
