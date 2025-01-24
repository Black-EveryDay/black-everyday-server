package com.ed.orderservice.presentaion.web.response.domain.create;

import com.ed.orderservice.application.port.in.dto.OrderItemDto;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.item.OrderItem;
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
        .orderItemDtos(OrderItem.toOrderItemDtos(order.getOrderItems()))
        .orderDeliveryInfo(OrderDeliveryInfo.
            toOrderDeliveryInfo(order.getOrderDelivery()))
        .build();
  }
}
