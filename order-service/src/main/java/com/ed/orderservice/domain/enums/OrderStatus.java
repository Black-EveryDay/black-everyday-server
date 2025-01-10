package com.ed.orderservice.domain.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
  ORDER_CREATED("01", "주문 생성"),
  PAYMENT_REQUEST("02", "결제 요청"),
  PAYMENT_WAITING("03", "결제 대기"),
  PAYMENT_CHECKING("04", "입금 확인 중"),
  PAYMENT_FAILED("05", "결제 실패"),
  PAYMENT_COMPLETED("06", "결제 완료"),
  SHIPPING_REQUEST("07", "배송 요청"),
  PAY_CANCEL_REQUEST("08", "결제 취소 예정"),
  PAY_CANCEL_COMPLETE("09", "결제 취소 완료");

  private final String code;
  private final String description;

  OrderStatus(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }
}

