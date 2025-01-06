package com.ed.authservice.libs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionStatus {
  //User
  USERNAME_ALREADY_USED(HttpStatus.CONFLICT, "u001", "Username already used"),
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