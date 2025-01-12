package com.ed.orderservice.application.service;


import com.ed.OrderPaymentCreateRequest;
import com.ed.orderservice.application.port.out.OrderPaymentCreateOutPort;
import com.ed.orderservice.domain.vo.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderPaymentCreateService {

  private final OrderPaymentCreateOutPort orderPaymentCreateOutPort;

  public void requestPaymentConfirmation(Order order) {
    OrderPaymentCreateRequest request = mapToOrderPaymentCreateRequest(order);
    orderPaymentCreateOutPort.sendPaymentConfirmRequest(request);
  }

  private OrderPaymentCreateRequest mapToOrderPaymentCreateRequest(Order order) {

    return OrderPaymentCreateRequest.newBuilder()
        .setUserId(order.getUserId())
        .setOrderId(order.getOrderPublicId())
        .setOrderName(order.getOrderName())
        .setRequestDateTime(order.getOrderTimeLine().getOrderDate())
        .setPaymentDeadline(order.getOrderTimeLine().getPaymentDeadline())
        .setOrderCancelDeadline(order.getOrderTimeLine().getOrderCancelDeadline())
        .setTotalAmount(order.getTotalAmount())
        .build();
  }

}
