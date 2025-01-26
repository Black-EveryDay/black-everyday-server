package com.ed.payment.application.port.out.persistence.dtos;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class SettleablePaymentResponse {

  private Long paymentId;
  private String orderPublicId;
}
