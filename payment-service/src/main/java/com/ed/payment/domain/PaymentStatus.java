package com.ed.payment.domain;

public enum PaymentStatus {
  READY,
  DONE,
  PARTIAL_CANCELED,
  CANCELED,
  ABORTED
  ;

  public static boolean isConfirmSuccess(PaymentStatus paymentStatus) {
    return paymentStatus == DONE;
  }

  public static boolean isCancelSuccess(PaymentStatus paymentStatus) {
    return paymentStatus == CANCELED || paymentStatus == PARTIAL_CANCELED;
  }

  public static PaymentStatus getConfirmStatus(PaymentStatus paymentStatus) {
    return DONE == paymentStatus ? DONE : ABORTED;
  }

  public static PaymentStatus getCancelStatus(PaymentStatus paymentStatus) {
    if (CANCELED == paymentStatus) return CANCELED;
    if (PARTIAL_CANCELED == paymentStatus) return PARTIAL_CANCELED;
    return ABORTED;
  }
}
