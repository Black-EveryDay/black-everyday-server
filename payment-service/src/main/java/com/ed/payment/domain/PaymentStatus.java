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

  public static boolean isCanceled(PaymentStatus paymentStatus) {
    return paymentStatus == CANCELED || paymentStatus == PARTIAL_CANCELED;
  }

  public static PaymentStatus getConfirmStatus(PaymentStatus paymentStatus) {
    return DONE == paymentStatus ? DONE : ABORTED;
  }

  public static PaymentStatus getCancelStatus(PaymentStatus paymentStatus) {
    if (isCanceled(paymentStatus)) return paymentStatus;
    return ABORTED;
  }
}
