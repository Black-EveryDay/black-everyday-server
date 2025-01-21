package com.ed.orderservice.infrastructure.db.mysql.adapter;

import com.ed.orderservice.application.port.out.OrderEventStatusUpdateOutPort;
import com.ed.orderservice.application.port.out.OrderStatusUpdateOutPort;
import com.ed.orderservice.domain.enums.OrderEventStatus;
import com.ed.orderservice.infrastructure.db.mysql.OrderEventJpaRepository;
import com.ed.orderservice.infrastructure.entity.OrderEventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventStatusUpdateAdapter implements OrderEventStatusUpdateOutPort {

  private final OrderEventJpaRepository orderEventJpaRepository;
  private final OrderStatusUpdateOutPort orderStatusUpdateOutPort;

  @Override
  public void updateToPaymentSuccess(String orderId) {
    updateOrderEventStatus(orderId, OrderEventStatus.ORDER_PAYMENT_SUCCESS);
    orderStatusUpdateOutPort.updateToPaymentWaiting(orderId);
  }

  @Override
  public void updateToPaymentFailure(String orderId) {
    updateOrderEventStatus(orderId, OrderEventStatus.ORDER_PAYMENT_FAILURE);
    orderStatusUpdateOutPort.updateToPaymentFailed(orderId);
  }

  private void updateOrderEventStatus(String orderId, OrderEventStatus newStatus) {

    //TODO error 및 로그
    OrderEventEntity orderEventEntity = orderEventJpaRepository.findByOrderEntity_OrderPublicId(
        orderId).orElseThrow();
    orderEventEntity.updateOrderEventStatus(newStatus);
    orderEventJpaRepository.save(orderEventEntity);
  }

}
