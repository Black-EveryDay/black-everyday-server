package com.ed.orderservice.domain.mapper;

import com.ed.orderservice.application.port.in.dto.OrderItemDto;
import com.ed.orderservice.domain.vo.order.item.OrderItem;
import com.ed.orderservice.domain.vo.order.item.OrderItemCoupon;
import com.ed.orderservice.infrastructure.entity.OrderItemEntity;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockPrepareRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

  public OrderItem toOrderItem(OrderItemDto dto) {
    OrderItem orderItem = OrderItem.builder()
        .brandId(dto.getBrandId())
        .productId(dto.getProductId())
        .productName(dto.getProductName())
        .quantity(dto.getQuantity())
        .unitPrice(dto.getUnitPrice())
        .size(dto.getSize())
        .productCategory(dto.getProductCategory())
        .build();

    if (dto.getOrderItemCouponId() != null) {
      OrderItemCoupon orderItemCoupon = OrderItemCoupon.builder()
          .orderCouponPublicId(dto.getOrderItemCouponId())
          .build();
      orderItem.updateOrderItemCoupon(orderItemCoupon);
    }

    return orderItem;
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
        .orderItemCoupon(Optional.ofNullable(orderItemEntity.getOrderItemCouponEntity())
            .map(OrderItemCoupon::entityToDomain)
            .orElse(null))
        .build();
  }

  public StockPrepareRequest toStockPrepareRequest(List<OrderItemDto> orderItemDtos) {
    return new StockPrepareRequest(orderItemDtos.stream()
        .map(this::convertToProductReservationInfo)
        .toList());
  }

  private StockPrepareRequest.ProductReservationInfo convertToProductReservationInfo(
      OrderItemDto orderItemDto) {
    return new StockPrepareRequest.ProductReservationInfo(
        orderItemDto.getProductId(),
        orderItemDto.getQuantity(),
        orderItemDto.getSize(),
        orderItemDto.getProductCategory()
    );
  }

}
