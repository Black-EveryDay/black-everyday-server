package com.ed.payment.application.port.out.persistence.dtos;

import static lombok.AccessLevel.PRIVATE;

import com.ed.payment.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class CreateCancelPaymentHistoryRequest {

  private Long paymentId;
  private String lastTransactionKey;
  private PaymentStatus paymentStatus;
  private Long totalAmount;
  private Long balanceAmount;
  private Long cancelAmount;
  private String cancelReason;
}
