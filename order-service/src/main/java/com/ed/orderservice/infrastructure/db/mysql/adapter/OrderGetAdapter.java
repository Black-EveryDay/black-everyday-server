package com.ed.orderservice.infrastructure.db.mysql.adapter;

import com.ed.orderservice.application.port.out.OrderGetOutPort;
import com.ed.orderservice.domain.mapper.OrderMapper;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.infrastructure.db.mysql.OrderJpaRepository;
import com.ed.orderservice.infrastructure.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderGetAdapter implements OrderGetOutPort {

  private final OrderJpaRepository orderJpaRepository;
  private final OrderMapper orderMapper;

  @Override
  public Order getOrder(String orderId) {
    OrderEntity orderEntity = orderJpaRepository.findByOrderPublicId(orderId);
    return orderMapper.toDomain(orderEntity);
  }
}
