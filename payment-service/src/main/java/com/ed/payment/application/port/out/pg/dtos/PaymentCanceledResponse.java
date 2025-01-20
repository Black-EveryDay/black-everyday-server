package com.ed.payment.application.port.out.pg.dtos;

import static lombok.AccessLevel.PRIVATE;

import com.ed.payment.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class PaymentCanceledResponse {

  private String paymentKey;
  private String orderId;
  private Long totalAmount;
  private Long balanceAmount;
  private Long cancelAmount;
  private String cancelReason;
  private PaymentStatus paymentStatus;
}
