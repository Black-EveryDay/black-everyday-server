package com.ed.payment.infrastructure.out.pg.toss;

import static com.ed.payment.domain.PaymentStatus.ABORTED;
import static com.ed.payment.domain.PaymentStatus.DONE;

import com.ed.payment.application.port.out.pg.PaymentDone;
import org.springframework.stereotype.Component;

@Component
class PaymentDoneMapper {

  PaymentDone mapToApplication(TossPaymentDone tossPaymentDone) {
    return PaymentDone.of(
        tossPaymentDone.getPaymentKey(),
        tossPaymentDone.getOrderId(),
        tossPaymentDone.getTotalAmount(),
        tossPaymentDone.getBalanceAmount(),
        isPaymentConfirmed(tossPaymentDone.getStatus()) ? DONE : ABORTED,
        tossPaymentDone.getLastTransactionKey());
  }

  private boolean isPaymentConfirmed(String paymentStatus) {
    return DONE.name().equalsIgnoreCase(paymentStatus);
  }
}
