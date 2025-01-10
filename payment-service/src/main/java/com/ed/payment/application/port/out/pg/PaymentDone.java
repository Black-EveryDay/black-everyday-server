package com.ed.payment.application.port.out.pg;

import static lombok.AccessLevel.PRIVATE;

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
  private String status;
  private String lastTransactionKey;

  public static PaymentDone of(
      String paymentKey, String orderId, Long totalAmount, Long balanceAmount,
      String status, String lastTransactionKey) {
    return PaymentDone.builder()
        .paymentKey(paymentKey)
        .orderId(orderId)
        .totalAmount(totalAmount)
        .balanceAmount(balanceAmount)
        .status(status)
        .lastTransactionKey(lastTransactionKey)
        .build();
  }
}
