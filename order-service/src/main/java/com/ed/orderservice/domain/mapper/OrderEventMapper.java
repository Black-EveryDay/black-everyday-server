package com.ed.orderservice.domain.mapper;

import com.ed.orderservice.domain.enums.OrderEventStatus;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.OrderEvent;
import com.ed.orderservice.infrastructure.entity.OrderEntity;
import com.ed.orderservice.infrastructure.entity.OrderEventEntity;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class OrderEventMapper {


  public OrderEventEntity toEntity(OrderEvent orderEvent, OrderEntity orderEntity) {
    OrderEventEntity orderEventEntity = OrderEventEntity.builder()
        .orderEventPublicId(orderEvent.getOrderEventPublicId())
        .orderEventStatus(orderEvent.getOrderEventStatus())
        .orderEntity(orderEntity)
        .play_Load(orderEvent.getPlayLoad())
        .build();

    orderEventEntity.addOrderStatusHistory(orderEvent.getOrderEventStatus());

    return orderEventEntity;
  }

  public OrderEvent toDomain(OrderEventEntity orderEventEntity, Order order) {
    return OrderEvent.builder()
        .orderEventId(orderEventEntity.getOrderEventId())
        .orderEventPublicId(orderEventEntity.getOrderEventPublicId())
        .orderEventStatus(orderEventEntity.getOrderEventStatus())
        .order(order)
        .playLoad(orderEventEntity.getPlay_Load())
        .build();
  }

  public OrderEvent toDomain(byte[] playLoad, Order order) {
    return OrderEvent.builder()
        .orderEventPublicId(UUID.randomUUID().toString())
        .orderEventStatus(OrderEventStatus.ORDER_PAYMENT_PENDING)
        .playLoad(playLoad)
        .order(order)
        .build();
  }

}
