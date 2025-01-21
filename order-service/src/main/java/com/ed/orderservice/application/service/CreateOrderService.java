package com.ed.orderservice.application.service;

import com.ed.OrderPaymentCreateRequestEvent;
import com.ed.orderservice.application.port.in.command.CreateOrderCommand;
import com.ed.orderservice.application.port.out.OrderCreatedOutPort;
import com.ed.orderservice.application.port.out.OrderEventCreatedOutPort;
import com.ed.orderservice.application.service.serializer.OrderPaymentRequestSerializer;
import com.ed.orderservice.domain.mapper.OrderMapper;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.OrderCreatedEvent;
import com.ed.orderservice.domain.vo.order.OrderEvent;
import com.ed.orderservice.presentaion.port.in.CreateOrderUseCase;
import java.util.concurrent.CompletableFuture;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {

  private final OrderMapper orderMapper;
  private final ProductStockService productStockService;
  private final OrderItemCouponService orderItemCouponService;
  private final OrderCreatedOutPort orderCreatedOutPort;
  private final OrderPaymentCreateService orderPaymentCreateService;
  private final OrderEventCreatedOutPort orderEventCreatedOutPort;
  private final OrderPaymentRequestSerializer orderPaymentRequestSerializer;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  @Transactional
  public Order createOrder(CreateOrderCommand orderCommand) {
    Order savedOrder = reserveStockApplyCouponsAndSaveOrder(orderCommand);
    OrderEvent savedOrderEvent = createAndSaveOrderPaymentEvent(savedOrder);

    publishOrderCreatedEvent(savedOrder, savedOrderEvent);
    return savedOrder;
  }

  @Async("asyncExecutor")
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public CompletableFuture<Void> handleOrderCreatedEvent(OrderCreatedEvent event) {
    return CompletableFuture.runAsync(() -> {
      try {
        orderPaymentCreateService.requestPaymentConfirmation(event.getOrderEvent(), event.getOrder());
      } catch (Exception e) {
        log.error("Failed to request payment confirmation for order: {}",
            event.getOrder().getOrderPublicId(), e);
      }
    }).exceptionally(ex -> {
      log.error("Unexpected error in handleOrderCreatedEvent", ex);
      return null;
    });
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

  private void publishOrderCreatedEvent(Order order, OrderEvent orderEvent) {
    eventPublisher.publishEvent(OrderCreatedEvent.builder()
        .order(order)
        .orderEvent(orderEvent)
        .build());
  }

}
