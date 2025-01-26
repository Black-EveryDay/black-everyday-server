package com.ed.orderservice.infrastructure.entity;

import com.ed.orderservice.domain.enums.OrderStatus;
import com.ed.orderservice.infrastructure.db.mysql.converter.OrderStatusConverter;
import com.ed.orderservice.infrastructure.entity.common.BaseTimeJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ED_ORDER_STATUS_HISTORY")
@NoArgsConstructor
public class OrderStatusHistoryEntity extends BaseTimeJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ORDER_STATUS_HISTORY_ID")
  private Long orderStatusHistoryId;

  @Convert(converter = OrderStatusConverter.class)
  @Column(name = "ORDER_STATUS", nullable = false)
  private OrderStatus orderStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ORDER_ID", nullable = false)
  private OrderEntity orderEntity;

  @Builder
  private OrderStatusHistoryEntity(OrderStatus orderStatus, OrderEntity orderEntity) {
    this.orderStatus = orderStatus;
    this.orderEntity = orderEntity;
  }

  public void updateOrder(OrderEntity orderEntity) {
    if (this.orderEntity != orderEntity) {
      this.orderEntity = orderEntity;
      if (!orderEntity.getOrderStatusHistoryEntity().contains(this)) {
        orderEntity.getOrderStatusHistoryEntity().add(this);
      }
    }
  }

}
