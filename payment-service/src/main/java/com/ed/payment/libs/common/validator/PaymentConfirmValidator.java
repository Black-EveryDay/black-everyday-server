package com.ed.payment.libs.common.validator;

import static com.ed.payment.domain.PaymentStatus.CANCELED;
import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.libs.common.exception.ErrorCode.EXPIRED_PAYMENT_CONFIRM_REQUEST;
import static com.ed.payment.libs.common.exception.ErrorCode.INVALID_PAYMENT_CONFIRM_AMOUNT;
import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_CONFIRM_NOT_ALLOWED;

import com.ed.payment.domain.Payment;
import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.libs.common.exception.CustomException;
import java.time.LocalDateTime;

public class PaymentConfirmValidator implements PaymentValidator {

  @Override
  public void validate(Payment payment, LocalDateTime requestDateTime, Long requestAmount) {
    validateConfirmablePaymentStatus(payment.getPaymentStatus());
    validateConfirmableDeadline(payment.getConfirmDeadline(), requestDateTime);
    validateConfirmAmount(payment.getTotalAmount(), requestAmount);
  }

  private void validateConfirmablePaymentStatus(PaymentStatus paymentStatus) {
    if (isNotConfirmablePaymentStatus(paymentStatus)) {
      throw new CustomException(PAYMENT_CONFIRM_NOT_ALLOWED);
    }
  }

  private void validateConfirmableDeadline(LocalDateTime confirmDeadline, LocalDateTime requestDateTime) {
    if (isNotConfirmableDeadline(confirmDeadline, requestDateTime)) {
      throw new CustomException(EXPIRED_PAYMENT_CONFIRM_REQUEST);
    }
  }

  private void validateConfirmAmount(Long totalAmount, Long requestAmount) {
    if (isNotValidConfirmAmount(totalAmount, requestAmount)) {
      throw new CustomException(INVALID_PAYMENT_CONFIRM_AMOUNT);
    }
  }

  private boolean isNotConfirmablePaymentStatus(PaymentStatus paymentStatus) {
    return paymentStatus == DONE || paymentStatus == CANCELED;
  }

  private boolean isNotConfirmableDeadline(LocalDateTime confirmDeadline, LocalDateTime requestDateTime) {
    return confirmDeadline.isBefore(requestDateTime);
  }

  private boolean isNotValidConfirmAmount(Long totalAmount, Long requestAmount) {
    return !totalAmount.equals(requestAmount);
  }
}
