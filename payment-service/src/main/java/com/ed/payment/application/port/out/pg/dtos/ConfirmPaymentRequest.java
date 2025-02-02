package com.ed.payment.application.port.out.pg.dtos;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class ConfirmPaymentRequest {

  private String paymentKey;
  private String orderId;
  private Long amount;
}
