package com.ed.payment.domain;

import static com.ed.payment.domain.PaymentStatus.CANCELED;
import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.domain.PaymentStatus.PARTIAL_CANCELED;
import static com.ed.payment.libs.common.exception.ErrorCode.EXPIRED_PAYMENT_CANCEL_REQUEST;
import static com.ed.payment.libs.common.exception.ErrorCode.EXPIRED_PAYMENT_CONFIRM_REQUEST;
import static com.ed.payment.libs.common.exception.ErrorCode.INVALID_PAYMENT_CANCEL_AMOUNT;
import static com.ed.payment.libs.common.exception.ErrorCode.INVALID_PAYMENT_CONFIRM_AMOUNT;
import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_CANCEL_NOT_ALLOWED;
import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_CONFIRM_NOT_ALLOWED;

import com.ed.payment.libs.common.exception.CustomException;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
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

  public void validateConfirmablePayment(LocalDateTime requestDateTime, Long requestAmount) {
    validateConfirmablePaymentStatus();
    validateConfirmableDeadline(requestDateTime);
    validateConfirmAmount(requestAmount);
  }

  public void validateCancelablePayment(LocalDateTime requestDateTime, Long requestAmount) {
    validateCancelablePaymentStatus();
    validateCancelableDeadline(requestDateTime);
    validateCancelAmount(requestAmount);
  }

  private void validateConfirmablePaymentStatus() {
    if (isNotConfirmablePaymentStatus()) {
      logBadPaymentStatus();
      throw new CustomException(PAYMENT_CONFIRM_NOT_ALLOWED);
    }
  }

  private void validateConfirmableDeadline(LocalDateTime requestDateTime) {
    if (isNotConfirmableDeadline(requestDateTime)) {
      logExpiredConfirmPaymentRequest(requestDateTime);
      throw new CustomException(EXPIRED_PAYMENT_CONFIRM_REQUEST);
    }
  }

  private void validateConfirmAmount(Long requestAmount) {
    if (isNotValidConfirmAmount(requestAmount)) {
      throw new CustomException(INVALID_PAYMENT_CONFIRM_AMOUNT);
    }
  }

  private void validateCancelablePaymentStatus() {
    if (isNotCancelablePaymentStatus()) {
      logBadPaymentStatus();
      throw new CustomException(PAYMENT_CANCEL_NOT_ALLOWED);
    }
  }

  private void validateCancelableDeadline(LocalDateTime requestDateTime) {
    if (isNotCancelableDeadline(requestDateTime)) {
      logExpiredCancelPaymentRequest(requestDateTime);
      throw new CustomException(EXPIRED_PAYMENT_CANCEL_REQUEST);
    }
  }

  private void validateCancelAmount(Long requestAmount) {
    if (isNotValidCancelAmount(requestAmount)) {
      throw new CustomException(INVALID_PAYMENT_CANCEL_AMOUNT);
    }
  }

  private boolean isNotConfirmablePaymentStatus() {
    return this.paymentStatus == DONE || this.paymentStatus == CANCELED;
  }

  private boolean isNotConfirmableDeadline(LocalDateTime requestDateTime) {
    return this.confirmDeadline.isBefore(requestDateTime);
  }

  private boolean isNotValidConfirmAmount(Long requestAmount) {
    return !this.totalAmount.equals(requestAmount);
  }

  private boolean isNotCancelablePaymentStatus() {
    return this.paymentStatus != DONE && this.paymentStatus != PARTIAL_CANCELED;
  }

  private boolean isNotCancelableDeadline(LocalDateTime requestDateTime) {
    return this.cancelDeadLine.isBefore(requestDateTime);
  }

  private boolean isNotValidCancelAmount(Long requestAmount) {
    return this.balanceAmount < requestAmount;
  }

  private void logBadPaymentStatus() {
    log.info("Not Allowed Payment Request = userId: {}, orderId: {}, paymentStatus: {}, messageTimestamp: {}",
        userId, orderPublicId, paymentStatus, LocalDateTime.now());
  }

  private void logExpiredConfirmPaymentRequest(LocalDateTime requestDateTime) {
    log.info("Expired Request = userId: {}, orderId: {}, requestDateTime: {}, confirmDeadline: {}, messageTimestamp: {}",
        userId, orderPublicId, requestDateTime, confirmDeadline, LocalDateTime.now());
  }

  private void logExpiredCancelPaymentRequest(LocalDateTime requestDateTime) {
    log.info("Expired Request = userId: {}, orderId: {}, requestDateTime: {}, cancelDeadLine: {}, messageTimestamp: {}",
        userId, orderPublicId, requestDateTime, cancelDeadLine, LocalDateTime.now());
  }
}
