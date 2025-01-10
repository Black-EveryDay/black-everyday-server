package com.ed.orderservice.application.port.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemDto {

  private String brandId;
  private String productId;
  private String productName;
  private Long quantity;
  private Long unitPrice;
  private String size;
  private String productCategory;

  @Builder
  public OrderItemDto(String brandId, String productId, String productName, Long quantity,
      Long unitPrice, String size, String productCategory) {
    this.brandId = brandId;
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.size = size;
    this.productCategory = productCategory;
  }
}
