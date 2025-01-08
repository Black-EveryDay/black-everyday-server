package com.ed.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Payment {

  private Long paymentId;
  private String paymentKey;
  private String orderId;
  private String orderName;
  private int amount;

  public boolean isValidAmount(int requestAmount) {
    return this.amount == requestAmount;
  }
}
