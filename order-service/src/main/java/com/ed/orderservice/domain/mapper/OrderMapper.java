package com.ed.orderservice.domain.mapper;

import com.ed.orderservice.application.port.in.command.CreateOrderCommand;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.OrderDelivery;
import com.ed.orderservice.domain.vo.order.OrderItem;
import com.ed.orderservice.domain.vo.order.Orderer;
import com.ed.orderservice.infrastructure.entity.OrderEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

  private final OrderItemMapper orderItemMapper;

  public Order toDomain(CreateOrderCommand command) {
    Orderer orderer = command.getOrderer();
    List<OrderItem> orderItems = command.getOrderItemDtos().stream()
        .map(orderItemMapper::toOrderItem)
        .toList();
    OrderDelivery orderDelivery = OrderDelivery.builder()
        .receiverInfo(command.getReceiverInfo())
        .receiverAddress(command.getReceiverAddress())
        .build();

    return Order.builder()
        .orderer(orderer)
        .orderItems(orderItems)
        .orderDelivery(orderDelivery)
        .build();
  }

  public Order toDomain(OrderEntity orderEntity) {
    Orderer orderer = Orderer.builder()
        .name(orderEntity.getOrderName())
        .phoneNumber(orderEntity.getPhoneNumber())
        .build();
    List<OrderItem> orderItems = orderEntity.getOrderItemEntitys().stream()
        .map(orderItemMapper::fromOrderItemEntity)
        .toList();

    OrderDelivery orderDelivery = OrderDelivery.
        fromOrderDeliveryEntity(orderEntity.getOrderDeliveryEntity());

    return Order.builder()
        .orderId(orderEntity.getOrderId())
        .orderer(orderer)
        .orderItems(orderItems)
        .orderDelivery(orderDelivery)
        .build();
  }

  public OrderEntity toEntity(Order newOrder) {
    OrderEntity orderEntity = OrderEntity.builder()
        .orderPublicId(newOrder.getOrderPublicId())
        .orderName(newOrder.getOrderName())
        .phoneNumber(newOrder.getPhoneNumber())
        .orderStatus(newOrder.getOrderStatus())
        .orderDate(LocalDateTime.now())
        .totalAmount(newOrder.getTotalAmount())
        .totalQuantity(newOrder.getTotalQuantity())
        .paymentId(newOrder.getPaymentId())
        .paidAt(newOrder.getPaidAt())
        .build();

    orderEntity.addOrderItems(newOrder.getOrderItems());
    orderEntity.addOrderStatuesHistory(newOrder.getOrderStatus());
    orderEntity.addOrderDeliveryEntity(newOrder.getOrderDelivery());

    return orderEntity;
  }

}
