package com.ed.orderservice.domain.enums;

import lombok.Getter;

@Getter
public enum OrderDeliveryStatus {
  ORDER_CONFIRMED("1","주문확인"),
  DELIVERY_REQUESTED("2","배송요청"),
  INVOICE_REGISTERED("3,","송장등록"),
  ORDER_DELIVERED("4","배송완료");

  private final String code;
  private final String description;

  OrderDeliveryStatus(String code, String description) {
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

