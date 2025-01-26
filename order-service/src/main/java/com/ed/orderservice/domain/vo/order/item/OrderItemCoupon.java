package com.ed.orderservice.domain.vo.order.item;

import com.ed.orderservice.infrastructure.entity.OrderItemCouponEntity;
import com.ed.orderservice.infrastructure.entity.OrderItemCouponTemplateEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemCoupon {

  private Long orderCouponId;
  private final String orderCouponPublicId;
  private OrderItemCouponTemplate orderItemCouponTemplate;

  @Builder
  private OrderItemCoupon(Long orderCouponId, String orderCouponPublicId,
      OrderItemCouponTemplate orderItemCouponTemplate) {
    this.orderCouponId = orderCouponId;
    this.orderCouponPublicId = orderCouponPublicId;
    this.orderItemCouponTemplate = orderItemCouponTemplate;
  }

  @Builder
  public OrderItemCoupon(String orderCouponPublicId) {
    this.orderCouponPublicId = orderCouponPublicId;
  }

  public OrderItemCouponEntity toEntity(OrderItemCoupon orderItemCoupon) {
    return OrderItemCouponEntity.builder()
        .orderCouponPublicId(orderItemCoupon.getOrderCouponPublicId())
        .orderItemCouponTemplateEntity(OrderItemCouponTemplateEntity.fromDomain(orderItemCoupon.getOrderItemCouponTemplate()))
        .build();
  }

  public static OrderItemCoupon entityToDomain(OrderItemCouponEntity orderItemCouponEntity) {
    return OrderItemCoupon.builder()
        .orderCouponPublicId(orderItemCouponEntity.getOrderCouponPublicId())
        .orderCouponId(orderItemCouponEntity.getOrderCouponId())
        .orderItemCouponTemplate(OrderItemCouponTemplate.fromEntity(
            orderItemCouponEntity.getOrderItemCouponTemplateEntity())
        )
        .build();
  }



}
