package com.ed.orderservice.infrastructure.db.mysql.adapter;

import com.ed.orderservice.application.port.out.OrderEventCreatedOutPort;
import com.ed.orderservice.domain.mapper.OrderEventMapper;
import com.ed.orderservice.domain.mapper.OrderMapper;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.OrderEvent;
import com.ed.orderservice.infrastructure.db.mysql.OrderEventJpaRepository;
import com.ed.orderservice.infrastructure.db.mysql.OrderJpaRepository;
import com.ed.orderservice.infrastructure.entity.OrderEntity;
import com.ed.orderservice.infrastructure.entity.OrderEventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventCreatedAdapter implements OrderEventCreatedOutPort {

  private final OrderJpaRepository orderJpaRepository;
  private final OrderMapper orderMapper;
  private final OrderEventJpaRepository orderEventJpaRepository;
  private final OrderEventMapper orderEventMapper;

  @Override
  public OrderEvent save(byte[] playLoad, String orderPublicId) {
    OrderEntity orderEntity = orderJpaRepository.findByOrderPublicId(orderPublicId);
    Order order = orderMapper.toDomain(orderEntity);
    OrderEvent orderEvent = orderEventMapper.toDomain(playLoad,order);
    OrderEventEntity orderEventEntity = orderEventMapper.toEntity(orderEvent, orderEntity);
    orderEventJpaRepository.save(orderEventEntity);
    return orderEventMapper.toDomain(orderEventEntity, order);
  }

}
