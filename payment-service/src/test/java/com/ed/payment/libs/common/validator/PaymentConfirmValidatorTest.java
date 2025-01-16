package com.ed.payment.libs.common.validator;

import static com.ed.payment.domain.PaymentStatus.CANCELED;
import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.domain.PaymentStatus.READY;
import static com.ed.payment.libs.common.exception.ErrorCode.EXPIRED_PAYMENT_CONFIRM_REQUEST;
import static com.ed.payment.libs.common.exception.ErrorCode.INVALID_PAYMENT_CONFIRM_AMOUNT;
import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_CONFIRM_NOT_ALLOWED;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ed.payment.domain.Payment;
import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.libs.common.exception.CustomException;
import com.ed.payment.libs.common.validator.dtos.PaymentValidatorRequest;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaymentConfirmValidatorTest {

  private final PaymentConfirmValidator confirmValidator = new PaymentConfirmValidator();

  @Test
  @DisplayName("validateConfirmablePayment: 결제가 준비 상태이며 승인 요청 금액이 결제 금액과 일치하고, 요청이 승인 기한 내에 온다면 유효성 검증을 통과한다.")
  void validateConfirmablePayment_success() {
    // given
    PaymentStatus ready = READY;
    final long totalAmount = 10000L;
    final long balanceAmount = 10000L;
    LocalDateTime confirmDeadline = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(ready, totalAmount, balanceAmount, confirmDeadline);

    LocalDateTime requestDateTime = LocalDateTime.now();
    final Long sameRequestAmount = 10000L;
    PaymentValidatorRequest request = PaymentValidatorRequest.of(payment, requestDateTime, sameRequestAmount);

    // expected
    Assertions.assertDoesNotThrow(() ->
        confirmValidator.validate(request));
  }

  @Test
  @DisplayName("validateConfirmablePayment: 결제가 이미 완료된 경우 유효성 검증을 실패한다.")
  void validateConfirmablePayment_fail_done_not_allowed_payment_status() {
    // given
    PaymentStatus done = DONE;
    final long totalAmount = 10000L;
    final long balanceAmount = 10000L;
    LocalDateTime confirmDeadline = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(done, totalAmount, balanceAmount, confirmDeadline);

    LocalDateTime requestDateTime = LocalDateTime.now();
    final Long sameRequestAmount = 10000L;
    PaymentValidatorRequest request = PaymentValidatorRequest.of(payment, requestDateTime, sameRequestAmount);

    // expected
    assertThatThrownBy(
        () -> confirmValidator.validate(request))
        .isInstanceOf(CustomException.class)
        .hasMessage(PAYMENT_CONFIRM_NOT_ALLOWED.getMessage());
  }

  @Test
  @DisplayName("validateConfirmablePayment: 결제가 취소된 경우 결제 승인 유효성 검증을 실패한다.")
  void validateConfirmablePayment_fail_canceled_not_allowed_payment_status() {
    // given
    PaymentStatus canceled = CANCELED;
    final long totalAmount = 10000L;
    final long balanceAmount = 10000L;
    LocalDateTime confirmDeadline = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(canceled, totalAmount, balanceAmount, confirmDeadline);

    LocalDateTime requestDateTime = LocalDateTime.now();
    final Long sameRequestAmount = 10000L;
    PaymentValidatorRequest request = PaymentValidatorRequest.of(payment, requestDateTime, sameRequestAmount);

    // expected
    assertThatThrownBy(
        () -> confirmValidator.validate(request))
        .isInstanceOf(CustomException.class)
        .hasMessage(PAYMENT_CONFIRM_NOT_ALLOWED.getMessage());
  }

  @Test
  @DisplayName("validateConfirmablePayment: 요청 시간이 결제 승인 기한을 지난 경우 유효성 검증을 실패한다.")
  void validateConfirmablePayment_fail_expired_confirm_deadline() {
    // given
    PaymentStatus ready = READY;
    final long totalAmount = 10000L;
    final long balanceAmount = 10000L;
    LocalDateTime confirmDeadline = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(ready, totalAmount, balanceAmount, confirmDeadline);

    LocalDateTime laterThanDeadline = LocalDateTime.now().plusDays(6);
    final Long sameRequestAmount = 10000L;
    PaymentValidatorRequest request = PaymentValidatorRequest.of(payment, laterThanDeadline, sameRequestAmount);

    // expected
    assertThatThrownBy(
        () -> confirmValidator.validate(request))
        .isInstanceOf(CustomException.class)
        .hasMessage(EXPIRED_PAYMENT_CONFIRM_REQUEST.getMessage());
  }

  @Test
  @DisplayName("validateConfirmablePayment: 주문 금액과 결제 요청 금액이 다른 경우 유효성 검증을 실패한다.")
  void validateConfirmablePayment_fail_invalid_confirm_amount() {
    // given
    PaymentStatus ready = READY;
    final long totalAmount = 10000L;
    final long balanceAmount = 10000L;
    LocalDateTime confirmDeadline = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(ready, totalAmount, balanceAmount, confirmDeadline);

    LocalDateTime requestDateTime = LocalDateTime.now();
    final Long differentAmount = 1000L;
    PaymentValidatorRequest request = PaymentValidatorRequest.of(payment, requestDateTime, differentAmount);

    // expected
    assertThatThrownBy(
        () -> confirmValidator.validate(request))
        .isInstanceOf(CustomException.class)
        .hasMessage(INVALID_PAYMENT_CONFIRM_AMOUNT.getMessage());
  }

  private Payment createPayment(
      PaymentStatus paymentStatus, Long totalAmount, Long balanceAmount,
      LocalDateTime confirmDeadline) {
    return Payment.builder()
        .paymentId(1L)
        .paymentPublicId(UUID.randomUUID().toString())
        .paymentKey("tgen_20250107154634hYNt7")
        .idempotencyKey(UUID.randomUUID().toString())
        .userId(UUID.randomUUID().toString())
        .paymentStatus(paymentStatus)
        .orderPublicId(UUID.randomUUID().toString())
        .orderName("피자맛 호방")
        .totalAmount(totalAmount)
        .balanceAmount(balanceAmount)
        .confirmDeadline(confirmDeadline)
        .cancelDeadLine(LocalDateTime.now().plusDays(5))
        .build();
  }
}