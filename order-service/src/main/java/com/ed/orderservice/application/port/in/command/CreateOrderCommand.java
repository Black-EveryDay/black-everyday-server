package com.ed.orderservice.application.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateOrderCommand {
	private final String orderId;
	private final String productId;
	private final Integer quantity;

	@Builder
	public CreateOrderCommand(String orderId, String productId, Integer quantity) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}
}
