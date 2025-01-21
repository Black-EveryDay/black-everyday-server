package com.ed.orderservice.presentaion.web.controller.internal;

import com.ed.orderservice.application.port.in.command.OrderSettlementCommand;
import com.ed.orderservice.presentaion.port.in.OrderSettlementUseCase;
import com.ed.orderservice.presentaion.web.response.domain.orderSettlement.OrderSettlementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/internal/orders")
public class OrderSettlementInternalController {

  private final OrderSettlementUseCase orderSettlementUseCase;

  @GetMapping("/{orderId}")
  public OrderSettlementResponse getOrderSettlement(@PathVariable String orderId) {

    return OrderSettlementResponse.from(orderSettlementUseCase.getOrderSettlement(
        OrderSettlementCommand.of(orderId)
        )
    );
  }

}
