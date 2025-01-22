package com.ed.orderservice.presentaion.web.controller.internal;

import com.ed.orderservice.application.port.in.command.OrderSettlementCommand;
import com.ed.orderservice.presentaion.port.in.OrderSettlementUseCase;
import com.ed.orderservice.presentaion.web.response.domain.orderSettlement.OrderSettlementResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/internal/orders")
public class OrderSettlementInternalController {

  private final OrderSettlementUseCase orderSettlementUseCase;

  @PostMapping("/settlements")
  public List<OrderSettlementResponse> getOrderSettlements(@RequestBody List<String> orderIds) {
    return orderIds.stream()
        .map(OrderSettlementCommand::of)
        .map(orderSettlementUseCase::getOrderSettlement)
        .map(OrderSettlementResponse::from)
        .toList();
  }

}
