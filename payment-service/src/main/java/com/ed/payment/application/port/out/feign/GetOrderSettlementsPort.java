package com.ed.payment.application.port.out.feign;

import com.ed.payment.application.port.out.feign.dtos.OrderSettlementResponse;
import java.util.List;

public interface GetOrderSettlementsPort {
  List<OrderSettlementResponse> getOrderSettlements(List<String> orderPublicIds);
}
