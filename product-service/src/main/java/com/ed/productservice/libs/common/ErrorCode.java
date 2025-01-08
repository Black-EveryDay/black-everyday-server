package com.ed.productservice.libs.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  BRAND_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 브랜드를 찾을 수 없습니다."),
  PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다."),
  STOCK_RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약된 상품정보를 찾을 수 없습니다."),
  INVENTORY_RESERVATION_FAILED(HttpStatus.BAD_REQUEST, "재고 예약에 실패했습니다."),
  INVENTORY_ALREADY_DECREASE(HttpStatus.BAD_REQUEST, "이미 차감된 재고 입니다."),
  INSUFFICIENT_PRODUCT_STOCK(HttpStatus.CONFLICT, "상품의 재고가 부족합니다.");


  private final HttpStatus status;
  private final String message;

  ErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
