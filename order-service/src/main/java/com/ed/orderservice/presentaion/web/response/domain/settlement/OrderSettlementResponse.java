package com.ed.orderservice.presentaion.web.response.domain.settlement;


import static lombok.AccessLevel.PRIVATE;
import com.ed.orderservice.domain.vo.order.settlement.OrderSettlement;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor(access = PRIVATE)
public class OrderSettlementResponse {

  private String orderPublicId;
  private List<OrderItemDetail> orderItems;

  @Builder
  private OrderSettlementResponse(String orderPublicId, List<OrderItemDetail> orderItems) {
    this.orderPublicId = orderPublicId;
    this.orderItems = orderItems;
  }

  public static OrderSettlementResponse from(OrderSettlement orderSettlement) {
    List<OrderItemDetail> orderItemDetails = orderSettlement.getOrderItems().stream()
        .map(OrderItemDetail::from)
        .collect(Collectors.toList());

    return OrderSettlementResponse.builder()
        .orderPublicId(orderSettlement.getOrderPublicId())
        .orderItems(orderItemDetails)
        .build();
  }
}

