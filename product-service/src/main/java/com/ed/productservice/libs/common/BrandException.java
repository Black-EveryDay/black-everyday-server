package com.ed.productservice.libs.common;

import lombok.Getter;

@Getter
public class BrandException extends RuntimeException {

  private final ErrorCode errorCode;

  public BrandException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}