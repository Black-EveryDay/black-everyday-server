package com.ed.orderservice.infrastructure.entity;

import com.ed.orderservice.domain.enums.OrderEventStatus;
import com.ed.orderservice.infrastructure.entity.common.BaseTimeJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "ED_ORDER_EVENT_STATUS_HISTORY")
@NoArgsConstructor
public class OrderEventStatusHistoryEntity extends BaseTimeJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ORDER_EVENT_STATUS_HISTORY_ID")
  private Long orderEventStatusHistoryId;

  @Enumerated(EnumType.STRING)
  @Column(name = "ORDER_EVENT_STATUS", nullable = false)
  private OrderEventStatus orderEventStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ORDER_EVENT_ID", nullable = false)
  private OrderEventEntity orderEventEntity;

  @Builder
  public OrderEventStatusHistoryEntity(OrderEventStatus orderEventStatus,
      OrderEventEntity orderEventEntity) {
    this.orderEventStatus = orderEventStatus;
    this.orderEventEntity = orderEventEntity;
  }

  public void updateOrder(OrderEventEntity orderEventEntity) {
    if (this.orderEventEntity != orderEventEntity) {
      this.orderEventEntity = orderEventEntity;
      if (!orderEventEntity.getOrderEventStatusHistoryEntity().contains(this)) {
        orderEventEntity.getOrderEventStatusHistoryEntity().add(this);
      }
    }
  }

}
