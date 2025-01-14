package com.ed.payment.domain;

import static com.ed.payment.domain.PaymentStatus.CANCELED;
import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.domain.PaymentStatus.READY;
import static com.ed.payment.libs.common.exception.ErrorCode.EXPIRED_PAYMENT_CANCEL_REQUEST;
import static com.ed.payment.libs.common.exception.ErrorCode.EXPIRED_PAYMENT_CONFIRM_REQUEST;
import static com.ed.payment.libs.common.exception.ErrorCode.INVALID_PAYMENT_CANCEL_AMOUNT;
import static com.ed.payment.libs.common.exception.ErrorCode.INVALID_PAYMENT_CONFIRM_AMOUNT;
import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_CANCEL_NOT_ALLOWED;
import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_CONFIRM_NOT_ALLOWED;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ed.payment.libs.common.exception.CustomException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaymentTest {

  @Test
  @DisplayName("validateConfirmablePayment: 결제가 준비 상태이며 승인 요청 금액이 결제 금액과 일치하고, 요청이 승인 기한 내에 온다면 유효성 검증을 통과한다.")
  void validateConfirmablePayment_success() {
  	// given
    PaymentStatus ready = READY;
    final long totalAmount = 10000L;
    final long balanceAmount = 10000L;
    LocalDateTime confirmDeadline = LocalDateTime.now().plusDays(5);
    LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(ready, totalAmount, balanceAmount,
        confirmDeadline, cancelDeadLine);

    LocalDateTime requestDateTime = LocalDateTime.now();
    final Long sameRequestAmount = 10000L;

    // expected
    Assertions.assertDoesNotThrow(() ->
        payment.validateConfirmablePayment(requestDateTime, sameRequestAmount));
  }

  @Test
  @DisplayName("validateConfirmablePayment: 결제가 이미 완료된 경우 유효성 검증을 실패한다.")
  void validateConfirmablePayment_fail_done_not_allowed_payment_status() {
    // given
    PaymentStatus done = DONE;
    final long totalAmount = 10000L;
    final long balanceAmount = 10000L;
    LocalDateTime confirmDeadline = LocalDateTime.now().plusDays(5);
    LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(done, totalAmount, balanceAmount,
        confirmDeadline, cancelDeadLine);

    LocalDateTime requestDateTime = LocalDateTime.now();
    final Long sameRequestAmount = 10000L;

    // expected
    assertThatThrownBy(
        () -> payment.validateConfirmablePayment(requestDateTime, sameRequestAmount))
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
    LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(canceled, totalAmount, balanceAmount,
        confirmDeadline, cancelDeadLine);

    LocalDateTime requestDateTime = LocalDateTime.now();
    final Long sameRequestAmount = 10000L;

    // expected
    assertThatThrownBy(
        () -> payment.validateConfirmablePayment(requestDateTime, sameRequestAmount))
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
    LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(ready, totalAmount, balanceAmount,
        confirmDeadline, cancelDeadLine);

    LocalDateTime laterThanDeadline = LocalDateTime.now().plusDays(6);
    final Long sameRequestAmount = 10000L;

    // expected
    assertThatThrownBy(
        () -> payment.validateConfirmablePayment(laterThanDeadline, sameRequestAmount))
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
    LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(ready, totalAmount, balanceAmount,
        confirmDeadline, cancelDeadLine);

    LocalDateTime requestDateTime = LocalDateTime.now();
    final Long differentAmount = 1000L;

    // expected
    assertThatThrownBy(
        () -> payment.validateConfirmablePayment(requestDateTime, differentAmount))
        .isInstanceOf(CustomException.class)
        .hasMessage(INVALID_PAYMENT_CONFIRM_AMOUNT.getMessage());
  }

  @Test
  @DisplayName("validateCancelablePayment: 결제 상태가 완료 혹은 부분 취소 상태이며 취소 요청 금액이 잔여 금액보다 같거나 작아야 하며, 요청이 취소 기한 내에 온다면 유효성 검증을 통과한다.")
  void validateCancelablePayment_success() {
    // given
    PaymentStatus done = DONE;
    final long totalAmount = 10000L;
    final long balanceAmount = 10000L;
    LocalDateTime confirmDeadline = LocalDateTime.now().plusDays(5);
    LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(
        done, totalAmount, balanceAmount,
        confirmDeadline, cancelDeadLine);

    LocalDateTime requestDateTime = LocalDateTime.now();
    final Long sameRequestAmount = 10000L;

    // expected
    Assertions.assertDoesNotThrow(() ->
        payment.validateCancelablePayment(requestDateTime, sameRequestAmount));
  }

  @Test
  @DisplayName("validateCancelablePayment: 결제 상태가 완료 혹은 부분 취소가 아니라면 요청이 취소 유효성 검증을 실패한다.")
  void validateCancelablePayment_fail_not_allowed_payment_status() {
    // given
    PaymentStatus notInDoneOrPartialCanceled = READY;
    final long totalAmount = 10000L;
    final long balanceAmount = 10000L;
    LocalDateTime confirmDeadline = LocalDateTime.now().plusDays(5);
    LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(notInDoneOrPartialCanceled, totalAmount, balanceAmount,
        confirmDeadline, cancelDeadLine);

    LocalDateTime requestDateTime = LocalDateTime.now();
    final Long sameRequestAmount = 10000L;

    // expected
    assertThatThrownBy(
        () -> payment.validateCancelablePayment(requestDateTime, sameRequestAmount))
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
    LocalDateTime confirmDeadline = LocalDateTime.now().plusDays(5);
    LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(
        done, totalAmount, balanceAmount,
        confirmDeadline, cancelDeadLine);

    LocalDateTime laterThanDeadline = LocalDateTime.now().plusDays(6);
    final Long sameRequestAmount = 10000L;

    // expected
    assertThatThrownBy(
        () -> payment.validateCancelablePayment(laterThanDeadline, sameRequestAmount))
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
    LocalDateTime confirmDeadline = LocalDateTime.now().plusDays(5);
    LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    Payment payment = createPayment(
        done, totalAmount, balanceAmount,
        confirmDeadline, cancelDeadLine);

    LocalDateTime requestDateTime = LocalDateTime.now();
    final Long greaterThanBalanceAmount = 20000L;

    // expected
    assertThatThrownBy(
        () -> payment.validateCancelablePayment(requestDateTime, greaterThanBalanceAmount))
        .isInstanceOf(CustomException.class)
        .hasMessage(INVALID_PAYMENT_CANCEL_AMOUNT.getMessage());
  }

  private Payment createPayment(
      PaymentStatus paymentStatus, Long totalAmount, Long balanceAmount,
      LocalDateTime confirmDeadline, LocalDateTime cancelDeadLine) {
    return new Payment(
        1L, UUID.randomUUID().toString(),
        "tgen_20250107154634hYNt7", UUID.randomUUID().toString(),
        UUID.randomUUID().toString(), paymentStatus,
        UUID.randomUUID().toString(), "피자맛 호빵",
        totalAmount, balanceAmount, confirmDeadline, cancelDeadLine);
  }
}