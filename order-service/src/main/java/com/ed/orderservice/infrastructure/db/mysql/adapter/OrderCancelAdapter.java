package com.ed.orderservice.infrastructure.db.mysql.adapter;

import com.ed.orderservice.application.port.out.OrderCancelOutPort;
import com.ed.orderservice.domain.enums.OrderStatus;
import com.ed.orderservice.domain.mapper.OrderMapper;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.infrastructure.db.mysql.OrderJpaRepository;
import com.ed.orderservice.infrastructure.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCancelAdapter implements OrderCancelOutPort {

  private final OrderJpaRepository orderJpaRepository;
  private final OrderMapper orderMapper;

  @Override
  public Order save(Order newOrder) {
    OrderEntity orderEntity = orderJpaRepository.findByOrderPublicIdAndUserId(newOrder.getOrderPublicId(), newOrder.getUserId());
    orderEntity.updateOrderStatus(OrderStatus.PAYMENT_CANCELED_WAITING);
    OrderEntity cancelEntity = orderJpaRepository.save(orderEntity);
    return orderMapper.toDomain(cancelEntity);
  }

}
