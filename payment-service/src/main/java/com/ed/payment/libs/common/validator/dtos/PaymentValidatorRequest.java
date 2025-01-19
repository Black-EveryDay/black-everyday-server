package com.ed.payment.libs.common.validator.dtos;

import static lombok.AccessLevel.PRIVATE;

import com.ed.payment.domain.Payment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
public class PaymentValidatorRequest {

  private Payment payment;
  private LocalDateTime requestDateTime;
  private Long requestAmount;

  public static PaymentValidatorRequest of(Payment payment, LocalDateTime requestDateTime, Long requestAmount) {
    return new PaymentValidatorRequest(payment, requestDateTime, requestAmount);
  }
}
