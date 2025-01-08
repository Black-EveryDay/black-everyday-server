package com.ed.authservice.libs.exception;

public class ServiceException extends BlackEveryDayException {

  public ServiceException(ExceptionStatus exceptionStatus) {
    super(exceptionStatus);
  }
}
