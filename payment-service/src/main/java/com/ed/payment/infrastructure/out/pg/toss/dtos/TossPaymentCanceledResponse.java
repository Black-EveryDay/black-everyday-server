package com.ed.payment.infrastructure.out.pg.toss.dtos;

import static lombok.AccessLevel.PRIVATE;

import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.infrastructure.out.pg.toss.retrofit.deserializer.PaymentStatusDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TossPaymentCanceledResponse {

  private String paymentKey;
  private String orderId;
  private Long totalAmount;
  private Long balanceAmount;
  @JsonDeserialize(using = PaymentStatusDeserializer.class)
  private PaymentStatus status;
  private List<Cancels> cancels;

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Cancels {
    private Long cancelAmount;
    private String cancelReason;
    private String canceledAt;
  }
}
