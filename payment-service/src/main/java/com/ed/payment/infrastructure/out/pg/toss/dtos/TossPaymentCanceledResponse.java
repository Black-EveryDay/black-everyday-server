package com.ed.payment.infrastructure.out.pg.toss.dtos;

import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.libs.common.deserializer.PaymentStatusDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.Getter;

@Getter
public class TossPaymentCanceledResponse {

  private String paymentKey;
  private String orderId;
  private Long totalAmount;
  private Long balanceAmount;
  @JsonDeserialize(using = PaymentStatusDeserializer.class)
  private PaymentStatus status;
  private String lastTransactionKey;
  private List<Cancels> cancels;

  @Getter
  public static class Cancels {
    private Long cancelAmount;
    private String cancelReason;
    private String canceledAt;
  }
}
