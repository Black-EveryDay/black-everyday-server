package com.ed.orderservice.infrastructure.db.mysql.adapter;

import com.ed.orderservice.application.port.out.OrderStatusUpdateOutPort;
import com.ed.orderservice.domain.enums.OrderStatus;
import com.ed.orderservice.infrastructure.db.mysql.OrderJpaRepository;
import com.ed.orderservice.infrastructure.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusUpdateAdapter implements OrderStatusUpdateOutPort {

  private final OrderJpaRepository orderJpaRepository;

  @Override
  public void updateToPaymentRequest(String orderId) {
    updateOrderStatus(orderId, OrderStatus.PAYMENT_REQUEST);
  }

  @Override
  public void updateToPaymentWaiting(String orderId) {
    updateOrderStatus(orderId, OrderStatus.PAYMENT_WAITING);
  }

  @Override
  public void updateToPaymentFailed(String orderId) {
    updateOrderStatus(orderId, OrderStatus.PAYMENT_FAILED);
  }

  @Override
  public void updateToPaymentCanceledRequest(String orderId) {
    updateOrderStatus(orderId, OrderStatus.PAYMENT_CANCELED_WAITING);
  }

  @Override
  public void updateToPaymentCanceled(String orderId) {
    updateOrderStatus(orderId, OrderStatus.PAYMENT_CANCELED);
  }

  @Override
  public void updateToPaymentCancelFailed(String orderId) {
    updateOrderStatus(orderId, OrderStatus.PAYMENT_CANCELED_FAILED);
  }

  private void updateOrderStatus(String orderId, OrderStatus status) {
    OrderEntity orderEntity = orderJpaRepository.findByOrderPublicId(orderId);
    orderEntity.updateOrderStatus(status);
    orderJpaRepository.save(orderEntity);
  }

}
