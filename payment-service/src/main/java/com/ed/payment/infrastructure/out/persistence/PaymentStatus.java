package com.ed.payment.infrastructure.out.persistence;

public enum PaymentStatus {
  READY,
  VERIFY_FAILED,
  DONE,
  CANCELED,
  ABORTED
}
