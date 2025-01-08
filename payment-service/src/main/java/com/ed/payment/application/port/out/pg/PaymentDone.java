package com.ed.payment.application.port.out.pg;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentDone {
  private String paymentKey;
  private String orderId;
  private int totalAmount;
  private int balanceAmount;
  private String status;
}
