package com.ed.orderservice.infrastructure.entity;

import com.ed.orderservice.domain.enums.OrderEventStatus;
import com.ed.orderservice.infrastructure.entity.common.BaseTimeByJpaEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ED_ORDER_EVENT")
@NoArgsConstructor
public class OrderEventEntity extends BaseTimeByJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ORDER_EVENT_ID")
  private Long orderEventId;

  @Column(
      name = "ORDER_EVENT_PUBLIC_ID",
      updatable = false,
      nullable = false,
      columnDefinition = "VARCHAR(36)")
  private String orderEventPublicId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", unique = true)
  private OrderEntity orderEntity;

  @Enumerated(EnumType.STRING)
  @Column(name = "ORDER_EVENT_STATUS", nullable = false)
  private OrderEventStatus orderEventStatus;

  @OneToMany(mappedBy = "orderEventEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderEventStatusHistoryEntity> orderEventStatusHistoryEntity = new ArrayList<>();

  @Column(name = "PLAY_LOAD")
  private byte[] play_Load;

  @Builder
  public OrderEventEntity(String orderEventPublicId, OrderEntity orderEntity,
      OrderEventStatus orderEventStatus,
      byte[] play_Load) {
    this.orderEventPublicId = orderEventPublicId;
    this.orderEntity = orderEntity;
    this.orderEventStatus = orderEventStatus;
    this.play_Load = play_Load;
  }

  public void updateOrderEventStatus(OrderEventStatus newEventStatus) {
    this.orderEventStatus = newEventStatus;
    addOrderStatusHistory(newEventStatus);
  }

  public void addOrderStatusHistory(OrderEventStatus orderEventStatus) {
    OrderEventStatusHistoryEntity newOrderStatusHistoryEntity =
        OrderEventStatusHistoryEntity.builder()
            .orderEventStatus(orderEventStatus)
            .orderEventEntity(this)
            .build();
    this.orderEventStatusHistoryEntity.add(newOrderStatusHistoryEntity);
  }
}
