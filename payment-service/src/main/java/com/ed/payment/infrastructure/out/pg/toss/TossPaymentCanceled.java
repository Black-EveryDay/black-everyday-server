package com.ed.payment.infrastructure.out.pg.toss;

import java.util.List;
import lombok.Getter;

@Getter
public class TossPaymentCanceled {
  private String paymentKey;
  private String orderId;
  private Long totalAmount;
  private Long balanceAmount;
  private String status;
  private String lastTransactionKey;
  private List<Cancels> cancels;

  @Getter
  public static class Cancels {
    private Long cancelAmount;
    private String cancelReason;
    private String canceledAt;
  }
}
