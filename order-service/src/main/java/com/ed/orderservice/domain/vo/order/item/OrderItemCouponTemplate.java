package com.ed.orderservice.domain.vo.order.item;

import com.ed.orderservice.infrastructure.entity.OrderItemCouponTemplateEntity;
import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.enums.DiscountType;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemCouponTemplate {

  private String couponTemplateId;
  private String couponName;
  private DiscountType discountType;
  private BigDecimal discountAmount;

  @Builder
  private OrderItemCouponTemplate(String couponTemplateId, String couponName,
      DiscountType discountType,
      BigDecimal discountAmount) {
    this.couponTemplateId = couponTemplateId;
    this.couponName = couponName;
    this.discountType = discountType;
    this.discountAmount = discountAmount;
  }

  public static OrderItemCouponTemplate fromEntity(OrderItemCouponTemplateEntity entity) {
    return OrderItemCouponTemplate.builder()
        .couponTemplateId(entity.getTemplateId())
        .couponName(entity.getCouponName())
        .discountType(entity.getDiscountType())
        .discountAmount(entity.getDiscountValue())
        .build();
  }

}
