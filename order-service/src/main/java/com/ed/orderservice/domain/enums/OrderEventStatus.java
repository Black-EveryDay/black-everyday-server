package com.ed.orderservice.domain.enums;


public enum OrderEventStatus {
  ORDER_PAYMENT_PENDING("주문 결제 대기"),
  ORDER_PAYMENT_SUCCESS("주문 결제 성공"),
  ORDER_PAYMENT_FAILURE("주문 결제 실패");

  private final String description;

  OrderEventStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
