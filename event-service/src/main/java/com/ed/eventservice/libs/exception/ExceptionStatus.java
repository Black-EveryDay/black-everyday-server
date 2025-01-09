package com.ed.eventservice.libs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionStatus {
  // CouponTemplate
  COUPON_TEMPLATE_NOT_FOUND(HttpStatus.BAD_REQUEST, "t001", "Coupon template not found"),

  // Coupon
  USER_NOT_OWNER_OF_COUPON(HttpStatus.FORBIDDEN, "c001", "User is not the owner of the coupon"),
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