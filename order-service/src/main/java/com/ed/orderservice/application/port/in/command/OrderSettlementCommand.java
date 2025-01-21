package com.ed.orderservice.application.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderSettlementCommand {

  private String orderId;

  @Builder
  private OrderSettlementCommand(String orderId) {
    this.orderId = orderId;
  }

  public static OrderSettlementCommand of(String orderId) {
    return OrderSettlementCommand.builder().orderId(orderId).build();
  }
}
