package com.ed.payment.application.port.in;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class ConfirmPaymentCommand {

  private String paymentType;
  private String paymentKey;
  private String orderId;
  private int amount;

  public static ConfirmPaymentCommand of(
      String paymentType, String paymentKey, String orderId, int amount) {
    return ConfirmPaymentCommand.builder()
        .paymentType(paymentType)
        .paymentKey(paymentKey)
        .orderId(orderId)
        .amount(amount)
        .build();
  }
}
