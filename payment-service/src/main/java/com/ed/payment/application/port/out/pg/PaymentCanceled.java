package com.ed.payment.application.port.out.pg;

import static lombok.AccessLevel.PRIVATE;

import com.ed.payment.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class PaymentCanceled {

  private String paymentKey;
  private String orderId;
  private Long totalAmount;
  private Long balanceAmount;
  private Long cancelAmount;
  private String cancelReason;
  private PaymentStatus paymentStatus;
  private String lastTransactionKey;

  public static PaymentCanceled of(
      String paymentKey, String orderId, Long totalAmount, Long balanceAmount, Long cancelAmount,
      String cancelReason, PaymentStatus paymentStatus, String lastTransactionKey) {
    return PaymentCanceled.builder()
        .paymentKey(paymentKey)
        .orderId(orderId)
        .totalAmount(totalAmount)
        .balanceAmount(balanceAmount)
        .cancelAmount(cancelAmount)
        .cancelReason(cancelReason)
        .paymentStatus(paymentStatus)
        .lastTransactionKey(lastTransactionKey)
        .build();
  }
}
