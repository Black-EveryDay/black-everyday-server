package com.ed.payment.infrastructure.out.feign.domain.order;

import com.ed.payment.application.port.out.feign.GetOrderSettlementsPort;
import com.ed.payment.application.port.out.feign.dtos.OrderSettlementResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrderFeignAdapter implements GetOrderSettlementsPort {

  private final OrderClient orderClient;

  @Override
  public List<OrderSettlementResponse> getOrderSettlements(List<String> orderPublicIds) {
    return orderClient.getOrderSettlement(orderPublicIds).getBody();
  }
}
