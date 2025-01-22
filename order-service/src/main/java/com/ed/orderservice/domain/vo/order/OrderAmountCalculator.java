package com.ed.orderservice.domain.vo.order;

import com.ed.orderservice.domain.vo.order.item.OrderItem;
import com.ed.orderservice.domain.vo.order.item.OrderItemCoupon;
import com.ed.orderservice.domain.vo.order.item.OrderItemCouponTemplate;
import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.enums.DiscountType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderAmountCalculator {

  private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);

  public long calculateTotalQuantity(List<OrderItem> orderItems) {
    return orderItems.stream()
        .mapToLong(OrderItem::getQuantity)
        .sum();
  }

  public long calculateTotalAmount(List<OrderItem> orderItems) {
    return orderItems.stream()
        .mapToLong(this::calculateItemTotalPrice)
        .sum();
  }

  private long calculateItemTotalPrice(OrderItem item) {
    long unitPrice = item.getUnitPrice();
    long quantity = item.getQuantity();

    if (!hasCoupon(item)) {
      return unitPrice * quantity;
    }

    return calculatePriceWithCoupon(item, unitPrice, quantity);
  }

  private boolean hasCoupon(OrderItem item) {
    OrderItemCoupon coupon = item.getOrderItemCoupon();
    return coupon != null
        && coupon.getOrderItemCouponTemplate() != null;
  }

  private long calculatePriceWithCoupon(OrderItem item, long unitPrice, long quantity) {
    OrderItemCouponTemplate template = item.getOrderItemCoupon().getOrderItemCouponTemplate();
    BigDecimal discountAmount = template.getDiscountAmount();

    return template.getDiscountType() == DiscountType.PERCENTAGE
        ? calculatePercentageDiscount(unitPrice, quantity, discountAmount)
        : calculateFixedDiscount(unitPrice, quantity, discountAmount);
  }

  private long calculatePercentageDiscount(long unitPrice, long quantity,
      BigDecimal discountPercentage) {
    BigDecimal discount = BigDecimal.valueOf(unitPrice)
        .multiply(discountPercentage.divide(HUNDRED))
        .setScale(0, RoundingMode.FLOOR);

    return (unitPrice - discount.longValue()) + (unitPrice * (quantity - 1));
  }

  private long calculateFixedDiscount(long unitPrice, long quantity, BigDecimal discountAmount) {
    return (unitPrice - discountAmount.longValue()) + (unitPrice * (quantity - 1));
  }
}
