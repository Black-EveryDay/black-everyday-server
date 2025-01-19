package com.ed.payment.infrastructure.out.pg.toss.dtos;

import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.libs.common.deserializer.PaymentStatusDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

@Getter
public class TossPaymentDoneResponse {

  private String paymentKey;
  private String orderId;
  private Long totalAmount;
  private Long balanceAmount;
  @JsonDeserialize(using = PaymentStatusDeserializer.class)
  private PaymentStatus status;
  private String lastTransactionKey;
}
