package com.ed.orderservice.presentaion.web.controller;

import static com.ed.orderservice.libs.common.HttpHeaderConstants.HEADER_USER_ID;

import com.ed.orderservice.application.port.in.command.CancelOrderCommand;
import com.ed.orderservice.presentaion.port.in.CancelOrderUseCase;
import com.ed.orderservice.presentaion.web.response.domain.cancel.OrderCancelResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderCancelController {

  private final CancelOrderUseCase cancelOrderUseCase;

  @PutMapping("/api/v1/orders/{orderId}/cancel")
  public OrderCancelResponse cancelOrder(
      @RequestHeader(HEADER_USER_ID) String userUuid,
      @PathVariable String orderId,
     @RequestParam(required = false) String cancelReason
  ) {
    return OrderCancelResponse.from(
        cancelOrderUseCase.cancelOrder(
            CancelOrderCommand.of(orderId, userUuid, cancelReason)
        )
    );
  }
}
