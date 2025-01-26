package com.ed.orderservice.presentaion.web.response.domain.settlement;

import static lombok.AccessLevel.PRIVATE;
import com.ed.orderservice.domain.vo.order.settlement.CouponSettlement;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class CouponDetail {
  private String couponTemplatePublicId;
  private String orderCouponPublicId;
  private BigDecimal discountAmount;

  @Builder
  private CouponDetail(String couponTemplatePublicId, String orderCouponPublicId,
      BigDecimal discountAmount) {
    this.couponTemplatePublicId = couponTemplatePublicId;
    this.orderCouponPublicId = orderCouponPublicId;
    this.discountAmount = discountAmount;
  }

  public static CouponDetail from(CouponSettlement couponSettlement) {
    return CouponDetail.builder()
        .couponTemplatePublicId(couponSettlement.getCouponTemplatePublicId())
        .orderCouponPublicId(couponSettlement.getOrderCouponPublicId())
        .discountAmount(couponSettlement.getDiscountAmount())
        .build();
  }

}
