package com.ed.payment.domain;

import static lombok.AccessLevel.PRIVATE;

import com.ed.payment.libs.common.validator.PaymentValidator;
import com.ed.payment.libs.common.validator.dtos.PaymentValidatorRequest;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class Payment {

  private Long paymentId;
  private String paymentPublicId;
  private String paymentKey;
  private String idempotencyKey;
  private String userId;
  private PaymentStatus paymentStatus;
  private String orderPublicId;
  private String orderName;
  private Long totalAmount;
  private Long balanceAmount;
  private LocalDateTime confirmDeadline;
  private LocalDateTime cancelDeadLine;

  public void validatePayment(PaymentValidator paymentValidator, PaymentValidatorRequest request) {
    paymentValidator.validate(request);
  }
}
