package com.ed.orderservice.infrastructure.entity;

import com.ed.orderservice.infrastructure.external.fegin.domain.event.enums.DiscountType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
@Embeddable
public class OrderItemCouponTemplateEntity {

  @Column(name = "TEMPLATE_ID", nullable = false, columnDefinition = "VARCHAR(36)")
  private String templateId;

  @Column(name = "COUPON_NAME")
  private String couponName;

  @Enumerated(EnumType.STRING)
  @Column(name = "DISCOUNT_TYPE")
  private DiscountType discountType;

  @Column(name = "DISCOUNT_VALUE", precision = 10, scale = 2)
  private BigDecimal discountValue;

}
