package com.ed.couponservice.libs.exception;

public class DomainException extends BlackEveryDayException {

  public DomainException(ExceptionStatus exceptionStatus) {
    super(exceptionStatus);
  }
}
