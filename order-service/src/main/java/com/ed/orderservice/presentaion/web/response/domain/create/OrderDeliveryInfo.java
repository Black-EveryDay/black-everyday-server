package com.ed.orderservice.presentaion.web.response.domain.create;

import com.ed.orderservice.domain.vo.order.OrderDelivery;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderDeliveryInfo {

  private final String receiverAddress;
  private final String receiverName;
  private final String receiverPhoneNumber;
  private final String receiverMobileNumber;
  private final String zipcode;
  private final String roadZipCode;
  private final String requirement;

  @Builder
  public OrderDeliveryInfo(OrderDelivery orderDelivery) {
    this.receiverAddress = orderDelivery.getReceiverAddress();
    this.receiverName = orderDelivery.getReceiverName();
    this.receiverPhoneNumber = orderDelivery.getReceiverPhoneNumber();
    this.receiverMobileNumber = orderDelivery.getReceiverMobileNumber();
    this.zipcode = orderDelivery.getZipcode();
    this.roadZipCode = orderDelivery.getRoadZipCode();
    this.requirement = orderDelivery.getRequirement();
  }

  public static OrderDeliveryInfo toOrderDeliveryInfo(OrderDelivery orderDelivery) {
    return OrderDeliveryInfo.builder()
        .orderDelivery(orderDelivery)
        .build();
  }
}
