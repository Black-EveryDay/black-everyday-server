package com.ed.orderservice.domain.vo.order.item;

import com.ed.orderservice.application.port.in.dto.OrderItemDto;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.infrastructure.entity.OrderItemEntity;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.ProductCategory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
  private int quantity;
  private Long unitPrice;
  private String size;
  private ProductCategory productCategory;
  private OrderItemCoupon orderItemCoupon = null;

  @Builder
  private OrderItem(String brandId,
      String productId, String productName, int quantity, Long unitPrice, String size,
      ProductCategory productCategory, OrderItemCoupon orderItemCoupon) {
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
    this.orderItemCoupon = orderItemCoupon;
  }

  public void updateOrderItemCoupon(OrderItemCoupon orderItemCoupon) {
    this.orderItemCoupon = orderItemCoupon;
  }

  public static OrderItemEntity fromOrderItem(OrderItem orderItem) {
    OrderItemEntity orderItemEntity = OrderItemEntity.builder()
        .orderItemPublicId(orderItem.getOrderItemPublicId())
        .brandId(orderItem.getBrandId())
        .productId(orderItem.getProductId())
        .productName(orderItem.getProductName())
        .quantity(orderItem.getQuantity())
        .unitPrice(orderItem.getUnitPrice())
        .size(orderItem.getSize())
        .productCategory(orderItem.getProductCategory())
        .build();

    orderItemEntity.addOrderItemCoupon(orderItem.getOrderItemCoupon());

    return orderItemEntity;
  }

  public static List<OrderItemDto> toOrderItemDtos(List<OrderItem> orderItems) {
    return orderItems.stream()
        .map(item -> OrderItemDto.builder()
            .brandId(item.getBrandId())
            .productId(item.getProductId())
            .productName(item.getProductName())
            .quantity(item.getQuantity())
            .unitPrice(item.getUnitPrice())
            .size(item.getSize())
            .productCategory(item.getProductCategory())
            .orderItemCouponId(Optional.ofNullable(item.orderItemCoupon)
                .map(OrderItemCoupon::getOrderCouponPublicId)
                .orElse(null))
            .build())
        .toList();
  }

}
