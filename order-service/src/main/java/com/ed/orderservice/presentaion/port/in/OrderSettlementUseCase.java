package com.ed.orderservice.presentaion.port.in;

import com.ed.orderservice.application.port.in.command.OrderSettlementCommand;
import com.ed.orderservice.domain.vo.order.settlement.OrderSettlement;
import java.util.List;

public interface OrderSettlementUseCase {
  List<OrderSettlement> getOrderSettlements(OrderSettlementCommand command) ;
}
