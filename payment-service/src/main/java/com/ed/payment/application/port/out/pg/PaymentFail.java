package com.ed.payment.application.port.out.pg;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class PaymentFail {
  private String code;
  private String message;
  private String orderId;

  public static PaymentFail of(String code, String message, String orderId) {
    return PaymentFail.builder()
        .code(code)
        .message(message)
        .orderId(orderId)
        .build();
  }
}
