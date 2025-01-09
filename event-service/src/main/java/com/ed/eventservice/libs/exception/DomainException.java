package com.ed.eventservice.libs.exception;

public class DomainException extends BlackEveryDayException {

  public DomainException(ExceptionStatus exceptionStatus) {
    super(exceptionStatus);
  }
}
