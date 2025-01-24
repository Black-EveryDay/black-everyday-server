package com.ed.orderservice.application.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CancelOrderCommand {

  private String userId;
  private String orderId;
  private String cancelReason;

  @Builder
  private CancelOrderCommand(String userId, String orderId, String cancelReason) {
    this.userId = userId;
    this.orderId = orderId;
    this.cancelReason = cancelReason;
  }

  public static CancelOrderCommand of(String orderId, String userId, String cancelReason) {
    return CancelOrderCommand.builder()
        .orderId(orderId)
        .userId(userId)
        .cancelReason(cancelReason)
        .build();
  }
}
