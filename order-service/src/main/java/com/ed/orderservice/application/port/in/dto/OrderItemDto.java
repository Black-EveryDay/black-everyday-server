package com.ed.orderservice.application.port.in.dto;

import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemDto {

  private String brandId;
  private String productId;
  private String productName;
  private int quantity;
  private Long unitPrice;
  private String size;
  private ProductCategory productCategory;
  private String orderItemCouponId;

  @Builder
  public OrderItemDto(String brandId, String productId, String productName, int quantity,
      Long unitPrice, String size, ProductCategory productCategory, String orderItemCouponId) {
    this.brandId = brandId;
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.size = size;
    this.productCategory = productCategory;
    this.orderItemCouponId = orderItemCouponId;
  }

}
