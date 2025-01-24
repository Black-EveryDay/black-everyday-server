package com.ed.orderservice.presentaion.web.response.domain.settlement;

import static lombok.AccessLevel.PRIVATE;
import com.ed.orderservice.domain.vo.order.settlement.OrderItemSettlement;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = PRIVATE)
public class OrderItemDetail {
    private String orderItemPublicId;
    private String brandPublicId;
    private String productPublicId;
    private int quantity;
    private Long unitPrice;
    private CouponDetail coupon;

    @Builder
    private OrderItemDetail(String orderItemPublicId, String brandPublicId, String productPublicId,
        int quantity, Long unitPrice, CouponDetail coupon) {
        this.orderItemPublicId = orderItemPublicId;
        this.brandPublicId = brandPublicId;
        this.productPublicId = productPublicId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.coupon = coupon;
    }

    public static OrderItemDetail from(OrderItemSettlement orderItemSettlement) {
      return OrderItemDetail.builder()
          .orderItemPublicId(orderItemSettlement.getOrderItemPublicId())
          .brandPublicId(orderItemSettlement.getBrandPublicId())
          .productPublicId(orderItemSettlement.getProductPublicId())
          .quantity(orderItemSettlement.getQuantity())
          .unitPrice(orderItemSettlement.getUnitPrice())
          .coupon(orderItemSettlement.getCoupon() != null ? CouponDetail.from(orderItemSettlement.getCoupon()) : null)
          .build();
    }
}
