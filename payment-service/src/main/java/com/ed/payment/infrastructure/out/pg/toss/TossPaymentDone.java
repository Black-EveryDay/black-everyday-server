package com.ed.payment.infrastructure.out.pg.toss;

import lombok.Getter;

@Getter
public class TossPaymentDone {
  private String paymentKey;
  private String orderId;
  private Long totalAmount;
  private Long balanceAmount;
  private String status;
  private String lastTransactionKey;
}
