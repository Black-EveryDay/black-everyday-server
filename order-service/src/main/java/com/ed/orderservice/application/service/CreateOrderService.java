package com.ed.orderservice.application.service;

import com.ed.orderservice.application.port.in.command.CreateOrderCommand;
import com.ed.orderservice.application.port.out.OrderCreatedOutPort;
import com.ed.orderservice.domain.mapper.OrderMapper;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.presentaion.port.in.CreateOrderUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {

  private final OrderMapper orderMapper;
  private final ProductStockService productStockService;
  private final OrderItemCouponService orderItemCouponService;
  private final OrderCreatedOutPort orderCreatedOutPort;

  @Override
  public Order createOrder(CreateOrderCommand orderCommand) {
    String inventoryReservationId = productStockService.reserveProductStock(orderCommand);
    Order orderWithReservedInventory = orderMapper.toDomain(orderCommand, inventoryReservationId);
    Order orderWithAppliedCoupons = orderItemCouponService.applyCoupons(orderWithReservedInventory);
    return orderCreatedOutPort.save(orderWithAppliedCoupons);
  }

}
