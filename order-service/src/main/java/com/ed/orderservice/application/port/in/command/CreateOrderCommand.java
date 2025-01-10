package com.ed.orderservice.application.port.in.command;

import com.ed.orderservice.application.port.in.OrderItemDto;
import com.ed.orderservice.domain.vo.order.OrderItem;
import com.ed.orderservice.domain.vo.order.Orderer;
import com.ed.orderservice.domain.vo.receiver.ReceiverAddress;
import com.ed.orderservice.domain.vo.receiver.ReceiverInfo;
import com.ed.orderservice.presentaion.web.request.OrderDeliveryRequest;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateOrderCommand {
	private Orderer orderer;
	private List<OrderItemDto> orderItemDtos;
	private ReceiverInfo receiverInfo;
	private ReceiverAddress receiverAddress;

	@Builder
	private CreateOrderCommand(Orderer orderer, List<OrderItemDto> orderItemDtos,
			OrderDeliveryRequest orderDeliveryRequest) {
		this.orderer = orderer;
		this.orderItemDtos = orderItemDtos;
		this.receiverInfo = ReceiverInfo.builder()
				.name(orderDeliveryRequest.getReceiverName())
				.requirement(orderDeliveryRequest.getRequirement())
				.mobileNumber(orderDeliveryRequest.getReceiverMobileNumber())
				.phoneNumber(orderDeliveryRequest.getReceiverPhoneNumber())
				.build();
		this.receiverAddress = ReceiverAddress.builder()
				.address(orderDeliveryRequest.getReceiverAddress())
				.zipCode(orderDeliveryRequest.getRoadZipCode())
				.roadZipCode(orderDeliveryRequest.getRoadZipCode())
				.build();
	}
}
