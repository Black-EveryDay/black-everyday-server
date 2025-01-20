package com.ed.payment.domain;

public enum PaymentStatus {
  READY,
  DONE,
  PARTIAL_CANCELED,
  CANCELED,
  ABORTED
  ;

  public static boolean isConfirmed(PaymentStatus paymentStatus) {
    return paymentStatus == DONE;
  }

  public static PaymentStatus getConfirmStatus(PaymentStatus paymentStatus) {
    return DONE == paymentStatus ? DONE : ABORTED;
  }

  public static boolean isCanceled(PaymentStatus paymentStatus) {
    return paymentStatus == CANCELED || paymentStatus == PARTIAL_CANCELED;
  }

  public static PaymentStatus getCancelPaymentStatus(PaymentStatus paymentStatus, Long totalAmount) {
    return isZeroTotalAmount(paymentStatus, totalAmount) ? CANCELED : paymentStatus;
  }

  private static boolean isZeroTotalAmount(PaymentStatus paymentStatus, Long totalAmount) {
    return paymentStatus == PARTIAL_CANCELED && totalAmount == 0L;
  }
}
