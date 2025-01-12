package com.ed.orderservice.presentaion.web.request;

import com.ed.orderservice.application.port.in.command.CreateOrderCommand;
import com.ed.orderservice.application.port.in.dto.OrderItemDto;
import com.ed.orderservice.domain.vo.order.Orderer;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewOrderRequest {

  private Orderer orderer;
  private List<OrderItemDto> orderItemDtos;
  private OrderDeliveryRequest orderDeliveryRequest;


  public static CreateOrderCommand toCommand(NewOrderRequest request, String userId) {
    return CreateOrderCommand.builder()
        .userId(userId)
        .orderer(request.getOrderer())
        .orderItemDtos(request.getOrderItemDtos())
        .orderDeliveryRequest(request.getOrderDeliveryRequest())
        .build();
  }
}
