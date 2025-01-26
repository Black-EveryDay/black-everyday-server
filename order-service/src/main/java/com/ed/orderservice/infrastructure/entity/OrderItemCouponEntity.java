package com.ed.orderservice.infrastructure.entity;

import com.ed.orderservice.infrastructure.entity.common.BaseTimeByJpaEntity;
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
public class OrderItemCouponEntity extends BaseTimeByJpaEntity {

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
  private OrderItemCouponTemplateEntity orderItemCouponTemplateEntity;

  @Builder
  private OrderItemCouponEntity(String orderCouponPublicId,
      OrderItemEntity orderItemEntity,
      OrderItemCouponTemplateEntity orderItemCouponTemplateEntity) {
    this.orderCouponPublicId = orderCouponPublicId;
    this.orderItemEntity = orderItemEntity;
    this.orderItemCouponTemplateEntity = orderItemCouponTemplateEntity;
  }

  @Builder
  private OrderItemCouponEntity(String orderCouponPublicId
  ) {
    this.orderCouponPublicId = orderCouponPublicId;
  }

  public void updateOrderItemEntity(OrderItemEntity orderItemEntity) {
    this.orderItemEntity = orderItemEntity;
  }

}
