package com.ed.orderservice.infrastructure.entity;

import com.ed.orderservice.domain.enums.OrderStatus;
import com.ed.orderservice.domain.vo.order.OrderDelivery;
import com.ed.orderservice.domain.vo.order.item.OrderItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ED_ORDER")
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ORDER_ID")
  private Long orderId;

  @Column(name = "ORDER_PUBLIC_ID", updatable = false, nullable = false)
  private String orderPublicId;

  @Column(name = "ORDER_NAME", nullable = false)
  private String orderName;

  @Column(name = "PHONE_NUMBER", nullable = false)
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  @Column(name = "ORDER_STATUS", nullable = false)
  private OrderStatus orderStatus;

  @Column(name = "ORDER_DATE", nullable = false)
  private LocalDateTime orderDate;

  @Column(name = "PAYMENT_DEADLINE", nullable = false)
  private LocalDateTime paymentDeadline;

  @Column(name = "ORDER_CANCEL_DEADLINE", nullable = false)
  private LocalDateTime orderCancelDeadline;

  @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItemEntity> orderItemEntitys = new ArrayList<>();

  @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderStatusHistoryEntity> orderStatusHistoryEntity = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "orderEntity")
  @JoinColumn(name = "order_delivery_id")
  private OrderDeliveryEntity orderDeliveryEntity;

  @Column(name = "TOTAL_AMOUNT", nullable = false)
  private Long totalAmount;

  @Column(name = "TOTAL_QUANTITY", nullable = false)
  private Long totalQuantity;

  @Column(name = "USER_ID", nullable = false)
  private String userid;

  @Column(name = "PAUMENT_ID", nullable = true)
  private String paymentId = null;
  @Column(name = "PAID_AT", nullable = true)
  private LocalDateTime paidAt = null;

  @Builder
  public OrderEntity(String orderPublicId, String orderName, String phoneNumber,
      OrderStatus orderStatus, LocalDateTime orderDate,
      Long totalAmount, Long totalQuantity, String userid, String paymentId, LocalDateTime paidAt) {
    this.orderPublicId = orderPublicId;
    this.orderName = orderName;
    this.phoneNumber = phoneNumber;
    this.orderStatus = orderStatus;
    this.orderDate = orderDate;
    this.totalAmount = totalAmount;
    this.totalQuantity = totalQuantity;
    this.userid = userid;
    this.paymentId = paymentId;
    this.paidAt = paidAt;
  }

  public void addOrderItems(List<OrderItem> items) {
    for (OrderItem item : items) {
      addOrderItem(item);
    }
  }

  private void addOrderItem(OrderItem orderItem) {
    OrderItemEntity orderItemEntity = OrderItem.fromOrderItem(orderItem);
    this.orderItemEntitys.add(orderItemEntity);
    orderItemEntity.updateOrder(this);
  }

  public void addOrderStatuesHistory(OrderStatus orderStatus) {
    OrderStatusHistoryEntity history =
        OrderStatusHistoryEntity.builder()
            .orderStatus(orderStatus)
            .build();

    this.orderStatusHistoryEntity = orderStatusHistoryEntity;
    history.updateOrder(this);
    this.orderStatusHistoryEntity.add(history);
  }

  public void addOrderDeliveryEntity(OrderDelivery orderDelivery) {
    OrderDeliveryEntity orderDeliveryEntity = OrderDelivery.fromOrderDelivery(orderDelivery);
    this.orderDeliveryEntity = orderDeliveryEntity;
    orderDeliveryEntity.updateOrder(this);
  }

  public void addOrderTimeline(LocalDateTime orderDate,
      LocalDateTime paymentDeadline, LocalDateTime orderCancelDeadline) {
    this.orderDate = orderDate;
    this.paymentDeadline = paymentDeadline;
    this.orderCancelDeadline = orderCancelDeadline;
  }

}
