package com.ed.productservice.libs.common;

import lombok.Getter;

@Getter
public class BrandNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public BrandNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}