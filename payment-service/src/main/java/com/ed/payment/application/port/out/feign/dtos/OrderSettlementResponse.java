package com.ed.payment.application.port.out.feign.dtos;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

@Getter
public class OrderSettlementResponse {

  private String orderPublicId;
  private List<OrderItemDetail> orderItems;

  @Getter
  public static class OrderItemDetail {

    private String orderItemPublicId;
    private String brandPublicId;
    private String productPublicId;
    private int quantity;
    private Long unitPrice;
    private CouponDetail coupon;
  }

  @Getter
  public static class CouponDetail {

    private String couponTemplatePublicId;
    private String orderCouponPublicId;
    private BigDecimal discountAmount;
  }
}