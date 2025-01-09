package com.ed.orderservice.infrastructure.db.mysql;

import com.ed.orderservice.application.port.out.OrderOutPort;
import com.ed.orderservice.domain.mapper.OrderMapper;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.infrastructure.entity.OrderEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class OrderAdapter implements OrderOutPort {
  private final OrderJpaRepository orderJpaRepository;
  private final OrderMapper orderMapper;

  @Override
  public Order save(Order newOrder) {
    OrderEntity orderEntity = orderMapper.toEntity(newOrder);
    OrderEntity savedEntity = orderJpaRepository.save(orderEntity);
    return orderMapper.toDomain(savedEntity);
  }
}
