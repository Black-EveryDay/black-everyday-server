package com.ed.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Payment {

  private Long paymentId;
  private String paymentKey;
  private String idempotencyKey;
  private String orderId;
  private String orderName;
  private Long amount;

  public boolean isValidAmount(Long requestAmount) {
    return this.amount.equals(requestAmount);
  }
}
