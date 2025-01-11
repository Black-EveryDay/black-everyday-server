package com.ed.payment.application.port.out.persistence;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponse {
  private String paymentPublicId;
  private String idempotencyKey;
  private String orderPublicId;
  private String orderName;
  private Long amount;
  private LocalDateTime confirmDeadline;
  private LocalDateTime cancelDeadLine;
}
