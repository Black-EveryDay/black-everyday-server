package com.ed.payment.application.port.in.command;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class PaymentRequestFailCommand {

  private String code;
  private String message;
  private String orderId;
}
