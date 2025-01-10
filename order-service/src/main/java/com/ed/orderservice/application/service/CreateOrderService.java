package com.ed.orderservice.application.service;

import com.ed.orderservice.application.port.out.OrderOutPort;
import com.ed.orderservice.domain.mapper.OrderMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.ed.orderservice.application.port.in.command.CreateOrderCommand;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.presentaion.port.in.CreateOrderUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateOrderService implements CreateOrderUseCase {
	private final OrderMapper orderMapper;
	private final OrderOutPort orderOutPort;

	@Override
	public Order createOrder(CreateOrderCommand command) {
		Order newOrder = orderMapper.toDomain(command);
		return orderOutPort.save(newOrder);
	}
}
