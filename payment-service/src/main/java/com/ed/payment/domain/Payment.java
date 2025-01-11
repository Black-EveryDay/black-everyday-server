package com.ed.payment.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Payment {

  private Long paymentId;
  private String paymentPublicId;
  private String paymentKey;
  private String idempotencyKey;
  private String userId;
  private PaymentStatus paymentStatus;
  private String orderPublicId;
  private String orderName;
  private Long amount;
  private LocalDateTime confirmDeadline;
  private LocalDateTime cancelDeadLine;

  public boolean isNotValidAmount(Long requestAmount) {
    return !this.amount.equals(requestAmount);
  }
}
