package com.ed.couponservice.libs.exception;

public class ServiceException extends BlackEveryDayException {

  public ServiceException(ExceptionStatus exceptionStatus) {
    super(exceptionStatus);
  }
}
