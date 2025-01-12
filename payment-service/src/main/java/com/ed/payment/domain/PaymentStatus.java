package com.ed.payment.domain;

public enum PaymentStatus {
  READY,
  VERIFY_FAILED,
  DONE,
  CANCELED,
  ABORTED
  ;

  public static boolean isSuccess(PaymentStatus paymentStatus) {
    return DONE == paymentStatus;
  }
}
