package com.ed.orderservice.presentaion.web.request;

import com.ed.orderservice.application.port.in.command.CreateOrderCommand;

import lombok.Getter;

@Getter
public class NewOrderRequest {
	String orderId;
	String productId;
	int quantity;

	public static CreateOrderCommand toCommand(NewOrderRequest request) {
		return CreateOrderCommand.builder()
			.orderId(request.getOrderId())
			.productId(request.getProductId())
			.quantity(request.getQuantity())
			.build();
	}
}
