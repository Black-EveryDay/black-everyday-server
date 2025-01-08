package com.ed.orderservice.presentaion.port.in;

import com.ed.orderservice.application.port.in.command.CreateOrderCommand;
import com.ed.orderservice.domain.vo.Order;

public interface CreateOrderUseCase {
	Order createOrder(CreateOrderCommand command);
}

