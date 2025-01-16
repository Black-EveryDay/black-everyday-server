package com.ed.payment.application.port.out.persistence.dtos;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class PaymentResponse {

  private String paymentPublicId;
  private String idempotencyKey;
  private String orderPublicId;
  private String orderName;
  private Long amount;
  private LocalDateTime confirmDeadline;
  private LocalDateTime cancelDeadLine;
}
