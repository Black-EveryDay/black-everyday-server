package com.ed.orderservice.presentaion.web.response;

import com.ed.orderservice.application.port.in.OrderItemDto;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.OrderItem;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewPurchaseOrderMessageResponse {

	private final List<OrderItemDto> orderItemDtos;
	private final OrderDeliveryInfo orderDeliveryInfo;

	public static NewPurchaseOrderMessageResponse from(Order order) {
		return NewPurchaseOrderMessageResponse.builder()
				.orderItemDtos(OrderItem.toOrderItemDTOs(order.getOrderItems()))
				.orderDeliveryInfo(OrderDeliveryInfo.
						toOrderDeliveryInfo(order.getOrderDelivery()))
				.build();
	}
}
