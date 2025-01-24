package com.ed.orderservice.application.service.domain.order.cancel;

import com.ed.OrderPaymentCancelRequestEvent;
import com.ed.orderservice.application.port.in.command.CancelOrderCommand;
import com.ed.orderservice.application.port.out.OrderCancelOutPort;
import com.ed.orderservice.application.port.out.OrderEventCancelOutPort;
import com.ed.orderservice.application.port.out.OrderGetOutPort;
import com.ed.orderservice.application.port.out.OrderStatusUpdateOutPort;
import com.ed.orderservice.application.service.domain.events.OrderEventDto;
import com.ed.orderservice.application.service.domain.order.cancel.serializer.OrderPaymentCancelRequestSerializer;
import com.ed.orderservice.application.service.domain.order.create.ProductStockService;
import com.ed.orderservice.application.service.domain.events.OrderEventPublisher;
import com.ed.orderservice.domain.enums.OrderEventType;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.OrderEvent;
import com.ed.orderservice.presentaion.port.in.CancelOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CancelOrderService implements CancelOrderUseCase {

  private final OrderGetOutPort orderGetOutPort;
  private final OrderEventPublisher orderEventPublisher;
  private final OrderPaymentCancelRequestSerializer orderPaymentCancelRequestSerializer;
  private final OrderItemCouponCancelService orderItemCouponCancelService;
  private final OrderEventCancelOutPort orderEventCancelOutPort;
  private final ProductStockService productStockService;
  private final OrderStatusUpdateOutPort orderStatusUpdateOutPort;

  @Override
  @Transactional
  public Order cancelOrder(CancelOrderCommand command) {
    Order cancelOrder = reserveStockApplyCouponsAndSaveOrder(command);
    OrderEvent savedOrderEvent = createAndSaveOrderPaymentCancelEventEvent(cancelOrder);
    OrderEventDto orderEventDto = OrderEventDto.createOrderEventDto(cancelOrder);

    orderEventPublisher.publishOrderEvent(orderEventDto, savedOrderEvent, OrderEventType.CANCELLED);

    return cancelOrder;
  }

  private Order reserveStockApplyCouponsAndSaveOrder(CancelOrderCommand command) {
    Order cancelOrder = orderGetOutPort.getOrder(command.getOrderId(), command.getUserId());
    productStockService.cancelProductStock(cancelOrder.getProductTransactionId());
    orderItemCouponCancelService.cancelCoupons(cancelOrder);
    orderStatusUpdateOutPort.updateToPaymentCanceledRequest(cancelOrder.getOrderPublicId());
    return cancelOrder;
  }


  private OrderEvent createAndSaveOrderPaymentCancelEventEvent(Order cancelOrder) {
    OrderPaymentCancelRequestEvent request = orderPaymentCancelRequestSerializer.mapToOrderPaymentCancelRequest(cancelOrder,"그냥");
    byte[] serializedRequest = orderPaymentCancelRequestSerializer.serializeToByteArray(request);
    return orderEventCancelOutPort.save(serializedRequest, cancelOrder.getOrderPublicId());
  }
}
