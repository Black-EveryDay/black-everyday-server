package com.ed.payment.application.port.out.pg;

import static lombok.AccessLevel.PRIVATE;

import com.ed.payment.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class PaymentDone {

  private String paymentKey;
  private String orderId;
  private Long totalAmount;
  private Long balanceAmount;
  private PaymentStatus paymentStatus;
  private String lastTransactionKey;

  public static PaymentDone of(
      String paymentKey, String orderId, Long totalAmount, Long balanceAmount,
      PaymentStatus paymentStatus, String lastTransactionKey) {
    return PaymentDone.builder()
        .paymentKey(paymentKey)
        .orderId(orderId)
        .totalAmount(totalAmount)
        .balanceAmount(balanceAmount)
        .paymentStatus(paymentStatus)
        .lastTransactionKey(lastTransactionKey)
        .build();
  }
}
