package com.ed.orderservice.presentaion.port.in;


import com.ed.orderservice.application.port.in.command.CancelOrderCommand;
import com.ed.orderservice.domain.vo.order.Order;

public interface CancelOrderUseCase {
  Order cancelOrder(CancelOrderCommand command);
}
