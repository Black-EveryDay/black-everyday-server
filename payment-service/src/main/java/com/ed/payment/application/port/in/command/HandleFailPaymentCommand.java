package com.ed.payment.application.port.in.command;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class HandleFailPaymentCommand {

  private String code;
  private String message;
  private String orderId;

  public static HandleFailPaymentCommand of(String code, String message, String orderId) {
    return HandleFailPaymentCommand.builder()
        .code(code)
        .message(message)
        .orderId(orderId)
        .build();
  }
}
