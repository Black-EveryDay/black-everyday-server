package com.ed.orderservice.domain.vo.order;

import com.ed.orderservice.application.port.in.OrderItemDto;
import com.ed.orderservice.infrastructure.entity.OrderItemEntity;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItem {
  private Long orderItemId = null;
  private String orderItemPublicId;
  private Order order = null;
  private String brandId;
  private String productId;
  private String productName;
  private Long quantity;
  private Long unitPrice;
  private String size;
  private String productCategory;

  @Builder
  private OrderItem(String brandId,
      String productId, String productName, Long quantity, Long unitPrice, String size,
      String productCategory) {
    this.orderItemId = null;
    this.orderItemPublicId = UUID.randomUUID().toString();
    this.order = null;
    this.brandId = brandId;
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.size = size;
    this.productCategory = productCategory;
  }

  public static OrderItemEntity fromOrderItem(OrderItem orderItem) {
    return OrderItemEntity.builder()
        .orderItemPublicId(orderItem.getOrderItemPublicId())
        .brandId(orderItem.getBrandId())
        .productId(orderItem.getProductId())
        .productName(orderItem.getProductName())
        .quantity(orderItem.getQuantity())
        .unitPrice(orderItem.getUnitPrice())
        .size(orderItem.getSize())
        .productCategory(orderItem.getProductCategory())
        .build();
  }

  public static List<OrderItemDto> toOrderItemDTOs(List<OrderItem> orderItems) {
    return orderItems.stream()
        .map(item -> OrderItemDto.builder()
            .brandId(item.getBrandId())
            .productId(item.getProductId())
            .productName(item.getProductName())
            .quantity(item.getQuantity())
            .unitPrice(item.getUnitPrice())
            .size(item.getSize())
            .productCategory(item.getProductCategory())
            .build())
        .collect(Collectors.toList());
  }

}
