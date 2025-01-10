package com.ed.orderservice.presentaion.web.response;

import com.ed.orderservice.domain.vo.order.OrderDelivery;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderDeliveryInfo {
  private String receiverAddress;
  private String receiverName;
  private String receiverPhoneNumber;
  private String receiverMobileNumber;
  private String zipcode;
  private String roadZipCode;
  private String requirement;

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
