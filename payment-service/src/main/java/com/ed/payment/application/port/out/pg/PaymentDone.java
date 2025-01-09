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
  private int totalAmount;
  private int balanceAmount;
  private String status;

  public static PaymentDone of(String paymentKey, String orderId, int totalAmount, int balanceAmount, String status) {
    return PaymentDone.builder()
        .paymentKey(paymentKey)
        .orderId(orderId)
        .totalAmount(totalAmount)
        .balanceAmount(balanceAmount)
        .status(status)
        .build();
  }
}
