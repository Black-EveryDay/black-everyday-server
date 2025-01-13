package com.ed.orderservice.infrastructure.db.mysql.adapter;

import com.ed.orderservice.application.port.out.OrderCreatedOutPort;
import com.ed.orderservice.domain.mapper.OrderMapper;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.infrastructure.db.mysql.OrderJpaRepository;
import com.ed.orderservice.infrastructure.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreatedAdapter implements OrderCreatedOutPort {

  private final OrderJpaRepository orderJpaRepository;
  private final OrderMapper orderMapper;

  @Override
  public Order save(Order newOrder) {
    OrderEntity orderEntity = orderMapper.toEntity(newOrder);
    OrderEntity savedEntity = orderJpaRepository.save(orderEntity);
    return orderMapper.toDomain(savedEntity);
  }

}
