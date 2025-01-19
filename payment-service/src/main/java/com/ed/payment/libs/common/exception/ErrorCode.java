package com.ed.payment.libs.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    COMMON_BAD_REQUEST(BAD_REQUEST, 0, "잘못된 요청입니다."),

    // Payment
    PAYMENT_NOT_FOUND(NOT_FOUND, 1000, "존재하지 않는 결제 정보입니다."),
    INVALID_PAYMENT_CONFIRM_AMOUNT(BAD_REQUEST, 1001, "결제 요청 금액이 유효하지 않습니다."),
    INVALID_PAYMENT_CANCEL_AMOUNT(BAD_REQUEST, 1002, "결제 취소 금액이 유효하지 않습니다."),
    PAYMENT_CONFIRM_NOT_ALLOWED(BAD_REQUEST, 1003, "이미 승인 및 취소 완료된 주문에 대한 승인 요청입니다."),
    PAYMENT_CANCEL_NOT_ALLOWED(BAD_REQUEST, 1004, "승인되지 않거나 취소된 결제에 대한 취소 요청입니다."),
    EXPIRED_PAYMENT_CONFIRM_REQUEST(BAD_REQUEST, 1005, "결제 승인 기한이 이미 만료된 요청입니다."),
    EXPIRED_PAYMENT_CANCEL_REQUEST(BAD_REQUEST, 1006, "결제 취소 기한이 이미 만료된 요청입니다."),

    ;
    private final HttpStatus status;
    private final Integer code;
    private final String message;
}