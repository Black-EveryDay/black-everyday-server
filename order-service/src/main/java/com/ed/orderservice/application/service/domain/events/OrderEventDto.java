package com.ed.orderservice.application.service.domain.events;

import com.ed.orderservice.domain.vo.order.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderEventDto {
  private String orderPublicId;
  private String userId;

  @Builder
  private OrderEventDto(String orderPublicId, String userId) {
    this.orderPublicId = orderPublicId;
    this.userId = userId;
  }

  public static OrderEventDto createOrderEventDto(Order order) {
    return OrderEventDto.builder()
        .orderPublicId(order.getOrderPublicId())
        .userId(order.getUserId())
        .build();
  }
}
