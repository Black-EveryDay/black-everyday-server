package com.ed.orderservice.domain.vo.order;

import com.ed.orderservice.domain.enums.OrderStatus;
import com.ed.orderservice.domain.vo.order.item.OrderItem;
import com.ed.orderservice.infrastructure.entity.OrderStatusHistoryEntity;
import com.ed.orderservice.libs.common.CommonUtils;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Order {

  private final OrderAmountCalculator amountCalculator = new OrderAmountCalculator();
  private final OrderTimeLine orderTimeLine = new OrderTimeLine();

  private Long orderId;
  private String orderPublicId;
  private String orderPublicName;

  private String orderName;
  private String phoneNumber;

  private List<OrderItem> orderItems = new ArrayList<>();
  private Long totalAmount = 0L;
  private Long totalQuantity = 0L;

  private OrderStatus orderStatus = OrderStatus.ORDER_CREATED;
  private List<OrderStatusHistoryEntity> orderStatusHistory = new ArrayList<>();

  private OrderDelivery orderDelivery;
  private String userId;

  private String productTransactionId;

  private String paymentId = null;
  private LocalDateTime paidAt;

  @Builder
  private Order(Long orderId, Orderer orderer,
      List<OrderItem> orderItems, OrderDelivery orderDelivery,
      String userId, String productTransactionId, OrderBase orderBase) {
    this.orderId = orderId;
    this.orderName = orderer.getName();
    this.phoneNumber = orderer.getPhoneNumber();
    this.orderItems = orderItems;
    this.orderDelivery = orderDelivery;
    this.userId = userId;
    this.productTransactionId = productTransactionId;
    this.paymentId = null;
    this.paidAt = null;
    this.orderPublicId = orderBase.getOrderPublicId();
    this.orderPublicName = orderBase.getOrderPublicName();
  }

  public void updateOrderTimelines() {
    orderTimeLine.updateOrderTimelines();
  }

  public void updatePaymentInfo(String paymentId, LocalDateTime paidAt) {
    this.paymentId = paymentId;
    this.paidAt = paidAt;
  }



  public void recalculateTotals() {
    this.totalQuantity = amountCalculator.calculateTotalQuantity(orderItems);
    this.totalAmount = amountCalculator.calculateTotalAmount(orderItems);
  }

}
