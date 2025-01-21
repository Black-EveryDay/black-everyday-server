package com.ed.orderservice.application.service;

import com.ed.orderservice.application.port.in.command.OrderSettlementCommand;
import com.ed.orderservice.application.port.out.OrderGetOutPort;
import com.ed.orderservice.domain.mapper.OrderSettlementMapper;
import com.ed.orderservice.domain.vo.order.settlement.OrderSettlement;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.presentaion.port.in.OrderSettlementUseCase;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderSettlementService implements OrderSettlementUseCase {

  private final OrderGetOutPort orderGetOutPort;
  private final OrderSettlementMapper orderSettlementMapper;

  @Override
  public OrderSettlement getOrderSettlement(OrderSettlementCommand command) {
    Order order = orderGetOutPort.getOrder(command.getOrderId());
    return orderSettlementMapper.toOrderSettlement(order);
  }
}
