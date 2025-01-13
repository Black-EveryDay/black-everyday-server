package com.ed.orderservice.infrastructure.entity;

import com.ed.orderservice.domain.vo.order.item.OrderItemCoupon;
import com.ed.orderservice.domain.vo.order.item.OrderItemCouponTemplate;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ED_ORDER_COUPON")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemCouponEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ORDER_COUPON_ID")
  private Long orderCouponId;

  @Column(
      name = "ORDER_COUPON_PUBLIC_ID",
      updatable = false,
      nullable = false,
      columnDefinition = "VARCHAR(36)")
  private String orderCouponPublicId;

  @OneToOne
  @JoinColumn(name = "ORDER_ITEM_ID", nullable = false)
  private OrderItemEntity orderItemEntity;

  @Embedded
  private OrderItemCouponTemplate orderItemCouponTemplate;

  @Builder
  public OrderItemCouponEntity(OrderItemCoupon orderItemCoupon) {
    this.orderItemCouponTemplate = orderItemCoupon.getOrderItemCouponTemplate();
    this.orderCouponPublicId = orderItemCoupon.getOrderCouponPublicId();
  }

  public void updateOrderItemEntity(OrderItemEntity orderItemEntity) {
    this.orderItemEntity = orderItemEntity;
  }
  
}
