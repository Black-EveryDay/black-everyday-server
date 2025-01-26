package com.ed.orderservice.application.service.domain.order.create;

import com.ed.OrderPaymentCreateRequestEvent;
import com.ed.orderservice.application.port.in.command.CreateOrderCommand;
import com.ed.orderservice.application.port.out.OrderCreatedOutPort;
import com.ed.orderservice.application.port.out.OrderEventCreatedOutPort;
import com.ed.orderservice.application.service.domain.events.OrderEventDto;
import com.ed.orderservice.application.service.domain.events.OrderEventPublisher;
import com.ed.orderservice.application.service.domain.order.create.serializer.OrderPaymentRequestSerializer;
import com.ed.orderservice.domain.enums.OrderEventType;
import com.ed.orderservice.domain.mapper.OrderMapper;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.OrderEvent;
import com.ed.orderservice.presentaion.port.in.CreateOrderUseCase;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {

  private final OrderMapper orderMapper;
  private final ProductStockService productStockService;
  private final OrderItemCouponService orderItemCouponService;
  private final OrderCreatedOutPort orderCreatedOutPort;
  private final OrderEventCreatedOutPort orderEventCreatedOutPort;
  private final OrderPaymentRequestSerializer orderPaymentRequestSerializer;
  private final OrderEventPublisher orderEventPublisher;

  @Override
  @Transactional
  public Order createOrder(CreateOrderCommand orderCommand) {
    Order savedOrder = reserveStockApplyCouponsAndSaveOrder(orderCommand);
    OrderEvent savedOrderEvent = createAndSaveOrderPaymentEvent(savedOrder);
    OrderEventDto orderEventDto = OrderEventDto.createOrderEventDto(savedOrder);

    orderEventPublisher.publishOrderEvent(orderEventDto, savedOrderEvent, OrderEventType.CREATED);
    return savedOrder;
  }

  private Order reserveStockApplyCouponsAndSaveOrder(CreateOrderCommand orderCommand) {
    String reserveProductStockId = productStockService.reserveProductStock(orderCommand);
    Order orderWithReservedInventory = orderMapper.toDomain(orderCommand, reserveProductStockId);
    Order orderWithAppliedCoupons = orderItemCouponService.applyCoupons(orderWithReservedInventory);
    return orderCreatedOutPort.save(orderWithAppliedCoupons);
  }

  private OrderEvent createAndSaveOrderPaymentEvent(Order savedOrder) {
    OrderPaymentCreateRequestEvent request = orderPaymentRequestSerializer.mapToOrderPaymentCreateRequest(savedOrder);
    byte[] serializedRequest = orderPaymentRequestSerializer.serializeToByteArray(request);
    return orderEventCreatedOutPort.save(serializedRequest, savedOrder.getOrderPublicId());
  }

}
