package com.ed.payment.application.port.out.persistence;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class PaymentResponse {
  private String paymentPublicId;
  private String idempotencyKey;
  private String orderPublicId;
  private String orderName;
  private Long amount;
  private LocalDateTime confirmDeadline;
  private LocalDateTime cancelDeadLine;

  public static PaymentResponse of(
      String paymentPublicId, String idempotencyKey,
      String orderPublicId, String orderName, Long amount,
      LocalDateTime confirmDeadline, LocalDateTime cancelDeadline) {
    return PaymentResponse.builder()
        .paymentPublicId(paymentPublicId)
        .idempotencyKey(idempotencyKey)
        .orderPublicId(orderPublicId)
        .orderName(orderName)
        .amount(amount)
        .confirmDeadline(confirmDeadline)
        .cancelDeadLine(cancelDeadline)
        .build();
  }
}
