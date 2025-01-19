package com.ed.eventservice.libs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionStatus {
  // CouponTemplate
  COUPON_TEMPLATE_NOT_FOUND(HttpStatus.BAD_REQUEST, "t001", "Coupon template not found"),
  COUPON_TEMPLATE_ISSUANCE_TYPE_NOT_AUTOMATIC(HttpStatus.BAD_REQUEST, "t002",
      "Coupon template issuance type is not automatic"),
  ;


  private final HttpStatus status;
  private final String customCode;
  private final String message;
  private final String err;

  ExceptionStatus(HttpStatus httpStatus, String customCode, String message) {
    this.status = httpStatus;
    this.customCode = customCode;
    this.message = message;
    this.err = httpStatus.getReasonPhrase();
  }
}