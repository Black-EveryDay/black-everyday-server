package com.ed.payment.libs.common.validator;

import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.domain.PaymentStatus.READY;
import static com.ed.payment.libs.common.exception.ErrorCode.EXPIRED_PAYMENT_CANCEL_REQUEST;
import static com.ed.payment.libs.common.exception.ErrorCode.INVALID_PAYMENT_CANCEL_AMOUNT;
import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_CANCEL_NOT_ALLOWED;
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

class PaymentCancelValidatorTest {

  private final PaymentCancelValidator cancelValidator = new PaymentCancelValidator();

  @Test
  @DisplayName("validateCancelablePayment: 결제 상태가 완료 혹은 부분 취소 상태이며 취소 요청 금액이 잔여 금액보다 같거나 작아야 하며, 요청이 취소 기한 내에 온다면 유효성 검증을 통과한다.")
  void validateCancelablePayment_success() {
    // given
    PaymentStatus done = DONE;
    final long totalAmount = 10000L;
    final long balanceAmount = 10000L;
    LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(done, totalAmount, balanceAmount, cancelDeadLine);

    LocalDateTime requestDateTime = LocalDateTime.now();
    final Long sameRequestAmount = 10000L;
    PaymentValidatorRequest request = PaymentValidatorRequest.of(payment, requestDateTime, sameRequestAmount);

    // expected
    Assertions.assertDoesNotThrow(() ->
        cancelValidator.validate(request));
  }

  @Test
  @DisplayName("validateCancelablePayment: 결제 상태가 완료 혹은 부분 취소가 아니라면 요청이 취소 유효성 검증을 실패한다.")
  void validateCancelablePayment_fail_not_allowed_payment_status() {
    // given
    PaymentStatus notInDoneOrPartialCanceled = READY;
    final long totalAmount = 10000L;
    final long balanceAmount = 10000L;
    LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(notInDoneOrPartialCanceled, totalAmount, balanceAmount, cancelDeadLine);

    LocalDateTime requestDateTime = LocalDateTime.now();
    final Long sameRequestAmount = 10000L;
    PaymentValidatorRequest request = PaymentValidatorRequest.of(payment, requestDateTime, sameRequestAmount);

    // expected
    assertThatThrownBy(
        () -> cancelValidator.validate(request))
        .isInstanceOf(CustomException.class)
        .hasMessage(PAYMENT_CANCEL_NOT_ALLOWED.getMessage());
  }

  @Test
  @DisplayName("validateCancelablePayment: 요청 시간이 결제 취소 기한을 지난 경우 유효성 검증을 실패한다.")
  void validateCancelablePayment_fail_expired_confirm_deadline() {
    // given
    PaymentStatus done = DONE;
    final long totalAmount = 10000L;
    final long balanceAmount = 10000L;
    LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(done, totalAmount, balanceAmount, cancelDeadLine);

    LocalDateTime laterThanDeadline = LocalDateTime.now().plusDays(6);
    final Long sameRequestAmount = 10000L;
    PaymentValidatorRequest request = PaymentValidatorRequest.of(payment, laterThanDeadline, sameRequestAmount);

    // expected
    assertThatThrownBy(
        () -> cancelValidator.validate(request))
        .isInstanceOf(CustomException.class)
        .hasMessage(EXPIRED_PAYMENT_CANCEL_REQUEST.getMessage());
  }

  @Test
  @DisplayName("validateCancelablePayment: 취소 요청 금액이 취소 가능 금액보다 큰 경우 유효성 검증을 실패한다.")
  void validateCancelablePayment_fail_invalid_cancel_amount() {
    // given
    PaymentStatus done = DONE;
    final long totalAmount = 10000L;
    final long balanceAmount = 10000L;
    LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(done, totalAmount, balanceAmount, cancelDeadLine);

    LocalDateTime requestDateTime = LocalDateTime.now();
    final Long greaterThanBalanceAmount = 20000L;
    PaymentValidatorRequest request = PaymentValidatorRequest.of(payment, requestDateTime, greaterThanBalanceAmount);

    // expected
    assertThatThrownBy(
        () -> cancelValidator.validate(request))
        .isInstanceOf(CustomException.class)
        .hasMessage(INVALID_PAYMENT_CANCEL_AMOUNT.getMessage());
  }

  private Payment createPayment(
      PaymentStatus paymentStatus, Long totalAmount, Long balanceAmount,
      LocalDateTime cancelDeadLine) {
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
        .confirmDeadline(LocalDateTime.now().plusDays(5))
        .cancelDeadLine(cancelDeadLine)
        .build();
  }
}