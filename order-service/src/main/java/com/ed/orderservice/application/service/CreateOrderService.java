package com.ed.orderservice.application.service;

import org.springframework.stereotype.Service;

import com.ed.orderservice.application.port.in.command.CreateOrderCommand;
import com.ed.orderservice.domain.vo.Order;
import com.ed.orderservice.presentaion.port.in.CreateOrderUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {

	private final CreateOrderUseCase createNewOrderUseCase;

	@Override
	public Order createOrder(CreateOrderCommand command) {
		return null;
	}
}
