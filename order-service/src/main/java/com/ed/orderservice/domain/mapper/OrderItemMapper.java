package com.ed.orderservice.domain.mapper;

import com.ed.orderservice.application.port.in.OrderItemDto;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.OrderItem;
import com.ed.orderservice.domain.vo.order.Orderer;
import com.ed.orderservice.infrastructure.entity.OrderEntity;
import com.ed.orderservice.infrastructure.entity.OrderItemEntity;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

  public OrderItem toOrderItem(OrderItemDto orderItem) {
    return OrderItem.builder()
        .brandId(orderItem.getBrandId())
        .productId(orderItem.getProductId())
        .productName(orderItem.getProductName())
        .quantity(orderItem.getQuantity())
        .unitPrice(orderItem.getUnitPrice())
        .size(orderItem.getSize())
        .productCategory(orderItem.getProductCategory())
        .build();
  }

  public OrderItem fromOrderItemEntity(OrderItemEntity orderItemEntity) {
    return OrderItem.builder()
        .brandId(orderItemEntity.getBrandId())
        .productId(orderItemEntity.getProductId())
        .productName(orderItemEntity.getProductName())
        .quantity(orderItemEntity.getQuantity())
        .unitPrice(orderItemEntity.getUnitPrice())
        .size(orderItemEntity.getSize())
        .productCategory(orderItemEntity.getProductCategory())
        .build();
  }

}
