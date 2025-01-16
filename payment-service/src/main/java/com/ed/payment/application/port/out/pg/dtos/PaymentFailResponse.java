package com.ed.payment.application.port.out.pg.dtos;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class PaymentFailResponse {

  private String code;
  private String message;
  private String orderId;

  public static PaymentFailResponse of(String code, String message, String orderId) {
    return PaymentFailResponse.builder()
        .code(code)
        .message(message)
        .orderId(orderId)
        .build();
  }
}
