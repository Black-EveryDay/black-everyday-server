package com.ed.orderservice.application.port.out.response;

import com.ed.orderservice.domain.vo.Order;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewPurchaseOrderMessageResponse {
	private final String orderId;

	public static NewPurchaseOrderMessageResponse from(Order order) {
		return NewPurchaseOrderMessageResponse.builder()
				.orderId(order.getOrderPublic())
				.build();
	}
}
