package com.ed.payment.domain;

import static lombok.AccessLevel.PRIVATE;

import com.ed.payment.application.port.out.feign.dtos.OrderSettlementResponse;
import com.ed.payment.application.port.out.feign.dtos.OrderSettlementResponse.OrderItemDetail;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
public class DailySettlement {

  private static final BigDecimal COMMISSION_RATE = BigDecimal.valueOf(0.1);

  private String orderPublicId;
  private String brandPublicId;
  private String productPublicId;
  private BigDecimal netRevenue;
  private BigDecimal discountAmount;
  private BigDecimal commission;

  public static List<DailySettlement> from(List<OrderSettlementResponse> responses) {
    return responses.stream()
        .flatMap(response -> response.getOrderItems().stream()
            .map(orderItem -> createFromOrderItem(response.getOrderPublicId(), orderItem)))
        .toList();
  }

  private static DailySettlement createFromOrderItem(String orderPublicId, OrderItemDetail orderItem) {

    BigDecimal totalAmount = calculateTotalRevenue(orderItem.getUnitPrice(), orderItem.getQuantity());
    BigDecimal commission = calculateCommission(totalAmount);
    BigDecimal netRevenue = totalAmount.subtract(getDiscountAmount(orderItem)).subtract(commission);

    return DailySettlement.builder()
        .orderPublicId(orderPublicId)
        .brandPublicId(orderItem.getBrandPublicId())
        .productPublicId(orderItem.getProductPublicId())
        .netRevenue(netRevenue)
        .discountAmount(getDiscountAmount(orderItem))
        .commission(commission)
        .build();
  }

  private static BigDecimal getDiscountAmount(OrderItemDetail orderItem) {
    return orderItem.getCoupon() == null ?  BigDecimal.ZERO : orderItem.getCoupon().getDiscountAmount();
  }

  private static BigDecimal calculateCommission(BigDecimal totalAmount) {
    return totalAmount.multiply(COMMISSION_RATE);
  }

  private static BigDecimal calculateTotalRevenue(Long unitPrice, int quantity) {
    return BigDecimal.valueOf(unitPrice)
        .multiply(BigDecimal.valueOf(quantity));
  }
}