package com.ed.orderservice.application.port.in.command;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderSettlementCommand {

  private List<String> orderIds;

  @Builder
  private OrderSettlementCommand(List<String> orderIds) {
    this.orderIds = orderIds;
  }

  public static OrderSettlementCommand of(List<String> orderIds) {
    return OrderSettlementCommand.builder().orderIds(orderIds).build();
  }
}
