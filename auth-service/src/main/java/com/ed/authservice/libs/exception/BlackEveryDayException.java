package com.ed.authservice.libs.exception;

import org.springframework.http.HttpStatus;

public class BlackEveryDayException extends RuntimeException {

  private final ExceptionStatus exceptionStatus;

  public BlackEveryDayException(ExceptionStatus exceptionStatus) {
    this.exceptionStatus = exceptionStatus;
  }

  @Override
  public String getMessage() {
    return exceptionStatus.getMessage();
  }

  public HttpStatus getHttpStatus() {
    return exceptionStatus.getStatus();
  }
}
