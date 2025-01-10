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
    PAYMENT_AMOUNT_MISMATCH(BAD_REQUEST, 1001, "요청 금액이 일치하지 않습니다."),
    DUPLICATED_ORDER_REQUEST(BAD_REQUEST, 1002, "이미 승인 및 취소된 주문 요청입니다."),

    // Payment History

    ;
    private final HttpStatus status;
    private final Integer code;
    private final String message;
}
