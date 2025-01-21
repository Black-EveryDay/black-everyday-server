package com.ed.orderservice.domain.mapper;

import com.ed.orderservice.application.port.in.command.CreateOrderCommand;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.OrderBase;
import com.ed.orderservice.domain.vo.order.OrderDelivery;
import com.ed.orderservice.domain.vo.order.Orderer;
import com.ed.orderservice.domain.vo.order.item.OrderItem;
import com.ed.orderservice.infrastructure.entity.OrderEntity;
import com.ed.orderservice.infrastructure.external.fegin.domain.event.dto.UseCouponDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

  private final OrderItemMapper orderItemMapper;

  public Order toDomain(CreateOrderCommand command, String productTransactionId) {
    Orderer orderer = command.getOrderer();
    List<OrderItem> orderItems = command.getOrderItemDtos().stream()
        .map(orderItemMapper::toOrderItem)
        .toList();
    OrderDelivery orderDelivery = OrderDelivery.builder()
        .receiver(command.getReceiver())
        .receiverAddress(command.getReceiverAddress())
        .build();
    OrderBase orderBase = OrderBase.builder()
        .orderPublicId(OrderBase.generateOrderPublicId())
        .orderPublicName(OrderBase.generatePublicName(orderItems))
        .build();

    Order order = Order.builder()
        .userId(command.getUserId())
        .orderer(orderer)
        .orderItems(orderItems)
        .orderDelivery(orderDelivery)
        .productTransactionId(productTransactionId)
        .orderBase(orderBase)
        .build();

    order.recalculateTotals();
    order.updateOrderTimelines();
    return order;
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

    OrderBase orderBase = OrderBase.builder()
        .orderPublicId(orderEntity.getOrderPublicId())
        .orderPublicName(orderEntity.getOrderPublicName())
        .build();

    Order newOrder = Order.builder()
        .userId(orderEntity.getUserid())
        .orderId(orderEntity.getOrderId())
        .orderer(orderer)
        .orderItems(orderItems)
        .orderDelivery(orderDelivery)
        .orderBase(orderBase)
        .productTransactionId(orderEntity.getProductTransactionId())
        .build();

    newOrder.recalculateTotals();
    newOrder.updateOrderTimelines();

    return newOrder;
  }

  public OrderEntity toEntity(Order newOrder) {
    OrderEntity orderEntity = OrderEntity.builder()
        .orderPublicId(newOrder.getOrderPublicId())
        .orderPublicName(newOrder.getOrderPublicName())
        .orderName(newOrder.getOrderName())
        .phoneNumber(newOrder.getPhoneNumber())
        .orderStatus(newOrder.getOrderStatus())
        .orderDate(LocalDateTime.now())
        .totalAmount(newOrder.getTotalAmount())
        .totalQuantity(newOrder.getTotalQuantity())
        .userid(newOrder.getUserId())
        .paymentId(newOrder.getPaymentId())
        .paidAt(newOrder.getPaidAt())
        .productTransactionId(newOrder.getProductTransactionId())
        .build();

    orderEntity.addOrderItems(newOrder.getOrderItems());
    orderEntity.addOrderStatusHistory(newOrder.getOrderStatus());
    orderEntity.addOrderDeliveryEntity(newOrder.getOrderDelivery());
    orderEntity.addOrderTimeline(newOrder.getOrderTimeLine().getOrderDate(),
        newOrder.getOrderTimeLine().getPaymentDeadline(),
        newOrder.getOrderTimeLine().getPaymentDeadline());

    return orderEntity;
  }

  public List<UseCouponDto> toUseCouponDto(Order order) {
    return order.getOrderItems().stream()
        .filter(orderItem -> orderItem.getOrderItemCoupon() != null)
        .map(orderItem -> UseCouponDto.builder()
            .orderId(order.getOrderPublicId())
            .userId(UUID.fromString(order.getUserId()))
            .brandId(UUID.fromString(orderItem.getBrandId()))
            .productId(UUID.fromString(orderItem.getProductId()))
            .couponPublicId(
                UUID.fromString(orderItem.getOrderItemCoupon().getOrderCouponPublicId()))
            .build())
        .toList();
  }

}
