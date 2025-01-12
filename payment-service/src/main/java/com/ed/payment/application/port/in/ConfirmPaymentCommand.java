package com.ed.payment.application.port.in;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class ConfirmPaymentCommand {

  private String paymentType;
  private String paymentKey;
  private String orderId;
  private Long amount;
  private LocalDateTime requestDateTime;

  public static ConfirmPaymentCommand of(
      String paymentType, String paymentKey, String orderId, Long amount) {
    return ConfirmPaymentCommand.builder()
        .paymentType(paymentType)
        .paymentKey(paymentKey)
        .orderId(orderId)
        .amount(amount)
        .requestDateTime(LocalDateTime.now())
        .build();
  }
}
