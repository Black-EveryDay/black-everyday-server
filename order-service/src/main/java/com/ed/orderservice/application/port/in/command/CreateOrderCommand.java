package com.ed.orderservice.application.port.in.command;

import com.ed.orderservice.application.port.in.dto.OrderItemDto;
import com.ed.orderservice.domain.vo.order.Orderer;
import com.ed.orderservice.domain.vo.receiver.Receiver;
import com.ed.orderservice.domain.vo.receiver.ReceiverAddress;
import com.ed.orderservice.presentaion.web.request.OrderDeliveryRequest;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateOrderCommand {

  private String userId;
  private Orderer orderer;
  private List<OrderItemDto> orderItemDtos;
  private Receiver receiver;
  private ReceiverAddress receiverAddress;

  @Builder
  private CreateOrderCommand(String userId, Orderer orderer, List<OrderItemDto> orderItemDtos,
      OrderDeliveryRequest orderDeliveryRequest) {
    this.userId = userId;
    this.orderer = orderer;
    this.orderItemDtos = orderItemDtos;
    this.receiver = Receiver.builder()
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

