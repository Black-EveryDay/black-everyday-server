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
    PAYMENT_NOT_MATCHED(NOT_FOUND, 1001, "조건에 만족하는 결제 정보가 없습니다."),
    PAYMENT_AMOUNT_MISMATCH(BAD_REQUEST, 1002, "요청 금액이 일치하지 않습니다."),
    DUPLICATED_ORDER_REQUEST(BAD_REQUEST, 1003, "이미 승인 및 취소 완료된 주문에 대한 요청입니다."),
    EXPIRED_PAYMENT_CONFIRM_REQUEST(BAD_REQUEST, 1004, "결제 기한이 이미 만료된 요청입니다."),

    // Payment History

    ;
    private final HttpStatus status;
    private final Integer code;
    private final String message;
}
