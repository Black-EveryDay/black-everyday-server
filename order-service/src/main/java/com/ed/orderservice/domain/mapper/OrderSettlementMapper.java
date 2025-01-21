package com.ed.orderservice.domain.mapper;

import com.ed.orderservice.domain.vo.order.settlement.CouponSettlement;
import com.ed.orderservice.domain.vo.order.settlement.OrderItemSettlement;
import com.ed.orderservice.domain.vo.order.settlement.OrderSettlement;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.item.OrderItem;
import com.ed.orderservice.domain.vo.order.item.OrderItemCoupon;
import com.ed.orderservice.domain.vo.order.item.OrderItemCouponTemplate;
import com.ed.orderservice.infrastructure.external.fegin.domain.event.enums.DiscountType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderSettlementMapper {

  public OrderSettlement toOrderSettlement(Order order) {
    return OrderSettlement.builder()
        .orderPublicId(order.getOrderPublicId())
        .orderItems(toOrderItemSettlements(order))
        .build();
  }

  private List<OrderItemSettlement> toOrderItemSettlements(Order order) {
    return order.getOrderItems().stream()
        .map(this::buildOrderItemSettlement)
        .collect(Collectors.toList());
  }

  private OrderItemSettlement buildOrderItemSettlement(OrderItem orderItem) {
    return OrderItemSettlement.builder()
        .orderItemPublicId(orderItem.getOrderItemPublicId())
        .brandPublicId(orderItem.getBrandId())
        .productPublicId(orderItem.getProductId())
        .quantity(orderItem.getQuantity())
        .unitPrice(orderItem.getUnitPrice())
        .coupon(buildCouponSettlement(orderItem))
        .build();
  }

  private CouponSettlement buildCouponSettlement(OrderItem orderItem) {
    if (orderItem.getOrderItemCoupon() == null) {
      return null;
    }

    OrderItemCoupon coupon = orderItem.getOrderItemCoupon();
    OrderItemCouponTemplate template = coupon.getOrderItemCouponTemplate();

    return CouponSettlement.builder()
        .couponTemplatePublicId(template.getCouponTemplateId())
        .orderCouponPublicId(coupon.getOrderCouponPublicId())
        .discountAmount(calculateDiscountAmount(orderItem))
        .build();
  }

  private BigDecimal calculateDiscountAmount(OrderItem orderItem) {
    if (orderItem.getOrderItemCoupon() == null) {
      return BigDecimal.ZERO;
    }

    OrderItemCouponTemplate template = orderItem.getOrderItemCoupon().getOrderItemCouponTemplate();
    BigDecimal unitPrice = BigDecimal.valueOf(orderItem.getUnitPrice());

    if (template.getDiscountType() == DiscountType.PERCENTAGE) {
      return unitPrice.multiply(template.getDiscountAmount())
          .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    } else if (template.getDiscountType() == DiscountType.FIXED) {
      return template.getDiscountAmount();
    }

    return BigDecimal.ZERO;
  }
}
