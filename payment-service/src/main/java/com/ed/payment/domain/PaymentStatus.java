package com.ed.payment.domain;

public enum PaymentStatus {
  READY,
  VERIFY_FAILED,
  DONE,
  CANCELED,
  ABORTED
  ;

  public static boolean isConfirmSuccess(PaymentStatus paymentStatus) {
    return DONE == paymentStatus;
  }

  public static boolean isCancelSuccess(PaymentStatus paymentStatus) {
    return CANCELED == paymentStatus;
  }
}
