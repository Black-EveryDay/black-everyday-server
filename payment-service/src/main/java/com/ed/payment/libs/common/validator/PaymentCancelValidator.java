package com.ed.payment.libs.common.validator;

import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.domain.PaymentStatus.PARTIAL_CANCELED;
import static com.ed.payment.libs.common.exception.ErrorCode.EXPIRED_PAYMENT_CANCEL_REQUEST;
import static com.ed.payment.libs.common.exception.ErrorCode.INVALID_PAYMENT_CANCEL_AMOUNT;
import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_CANCEL_NOT_ALLOWED;

import com.ed.payment.domain.Payment;
import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.libs.common.exception.CustomException;
import java.time.LocalDateTime;

public class PaymentCancelValidator implements PaymentValidator {

  @Override
  public void validate(Payment payment, LocalDateTime requestDateTime, Long requestAmount) {
    validateCancelablePaymentStatus(payment.getPaymentStatus());
    validateCancelableDeadline(payment.getCancelDeadLine(), requestDateTime);
    validateCancelAmount(payment.getBalanceAmount(), requestAmount);
  }

  private void validateCancelablePaymentStatus(PaymentStatus paymentStatus) {
    if (isNotCancelablePaymentStatus(paymentStatus)) {
      throw new CustomException(PAYMENT_CANCEL_NOT_ALLOWED);
    }
  }

  private void validateCancelableDeadline(LocalDateTime cancelDeadLine, LocalDateTime requestDateTime) {
    if (isNotCancelableDeadline(cancelDeadLine, requestDateTime)) {
      throw new CustomException(EXPIRED_PAYMENT_CANCEL_REQUEST);
    }
  }

  private void validateCancelAmount(Long balanceAmount, Long requestAmount) {
    if (isNotValidCancelAmount(balanceAmount, requestAmount)) {
      throw new CustomException(INVALID_PAYMENT_CANCEL_AMOUNT);
    }
  }

  private boolean isNotCancelablePaymentStatus(PaymentStatus paymentStatus) {
    return paymentStatus != DONE && paymentStatus != PARTIAL_CANCELED;
  }

  private boolean isNotCancelableDeadline(LocalDateTime cancelDeadLine, LocalDateTime requestDateTime) {
    return cancelDeadLine.isBefore(requestDateTime);
  }

  private boolean isNotValidCancelAmount(Long balanceAmount, Long requestAmount) {
    return balanceAmount < requestAmount;
  }
}
