package com.ed.orderservice.infrastructure.entity;

import com.ed.orderservice.domain.vo.order.item.OrderItemCouponTemplate;
import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.enums.DiscountType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class OrderItemCouponTemplateEntity {

  @Column(name = "COUPON_TEMPLATE_ID", nullable = false, columnDefinition = "VARCHAR(36)")
  private String templateId;

  @Column(name = "COUPON_NAME")
  private String couponName;

  @Column(name = "DISCOUNT_TYPE")
  @Enumerated(EnumType.STRING)
  private DiscountType discountType;

  @Column(name = "DISCOUNT_VALUE", precision = 10, scale = 2)
  private BigDecimal discountValue;

  @Builder
  private OrderItemCouponTemplateEntity(String templateId, String couponName,
      DiscountType discountType, BigDecimal discountValue) {
    this.templateId = templateId;
    this.couponName = couponName;
    this.discountType = discountType;
    this.discountValue = discountValue;
  }

  public static OrderItemCouponTemplateEntity fromDomain(OrderItemCouponTemplate domain) {
    return OrderItemCouponTemplateEntity.builder()
        .templateId(domain.getCouponTemplateId())
        .couponName(domain.getCouponName())
        .discountType(domain.getDiscountType())
        .discountValue(domain.getDiscountAmount())
        .build();
  }

}
