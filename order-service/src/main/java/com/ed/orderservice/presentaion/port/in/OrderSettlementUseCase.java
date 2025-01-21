package com.ed.orderservice.presentaion.port.in;

import com.ed.orderservice.application.port.in.command.OrderSettlementCommand;
import com.ed.orderservice.domain.vo.order.settlement.OrderSettlement;

public interface OrderSettlementUseCase {
  OrderSettlement getOrderSettlement(OrderSettlementCommand command) ;
}
