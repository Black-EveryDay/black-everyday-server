package com.ed.orderservice.domain.vo.order;

import com.ed.orderservice.domain.enums.DeliveryCompanyCode;
import com.ed.orderservice.domain.enums.OrderDeliveryStatus;
import com.ed.orderservice.domain.vo.receiver.ReceiverAddress;
import com.ed.orderservice.domain.vo.receiver.ReceiverInfo;
import com.ed.orderservice.infrastructure.entity.OrderDeliveryEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderDelivery {
  private String receiverName;
  private String receiverAddress;
  private String receiverPhoneNumber;
  private String receiverMobileNumber;
  private String zipcode;
  private String roadZipCode;
  private String requirement;
  private OrderDeliveryStatus orderDeliveryStatus = OrderDeliveryStatus.ORDER_CONFIRMED;
  private DeliveryCompanyCode deliveryCompanyCode = null;
  private String invoiceNumber= null;

  @Builder
  private OrderDelivery(ReceiverInfo receiverInfo, ReceiverAddress receiverAddress
      ) {
    this.receiverName = receiverInfo.getName();
    this.receiverPhoneNumber = receiverInfo.getPhoneNumber();
    this.receiverMobileNumber = receiverInfo.getMobileNumber();
    this.requirement = receiverInfo.getRequirement();
    this.receiverAddress = receiverAddress.getAddress();
    this.zipcode = receiverAddress.getRoadZipCode();
    this.roadZipCode = receiverAddress.getRoadZipCode();
  }

  public void updateDeliveryInfo(DeliveryCompanyCode deliveryCompanyCode,
      String invoiceNumber) {
    this.deliveryCompanyCode = deliveryCompanyCode;
    this.invoiceNumber = invoiceNumber;
  }

  public static OrderDeliveryEntity fromOrderDelivery(OrderDelivery orderDelivery) {
    return OrderDeliveryEntity.builder()
        .receiverName(orderDelivery.getReceiverName())
        .receiverAddress(orderDelivery.getReceiverAddress())
        .receiverPhoneNumber(orderDelivery.getReceiverPhoneNumber())
        .receiverMobileNumber(orderDelivery.getReceiverMobileNumber())
        .zipcode(orderDelivery.getZipcode())
        .roadZipCode(orderDelivery.getRoadZipCode())
        .requirement(orderDelivery.getRequirement())
        .orderDeliveryStatus(OrderDeliveryStatus.ORDER_CONFIRMED)
        .deliveryCompanyCode(null)
        .invoiceNumber(null)
        .build();
  }

  public static OrderDelivery fromOrderDeliveryEntity(OrderDeliveryEntity orderDeliveryEntity) {
    ReceiverInfo receiverInfo = ReceiverInfo.builder()
        .name(orderDeliveryEntity.getReceiverName())
        .phoneNumber(orderDeliveryEntity.getReceiverPhoneNumber())
        .mobileNumber(orderDeliveryEntity.getReceiverMobileNumber())
        .requirement(orderDeliveryEntity.getRequirement())
        .build();

    ReceiverAddress receiverAddress = ReceiverAddress.builder()
        .address(orderDeliveryEntity.getReceiverAddress())
        .roadZipCode(orderDeliveryEntity.getRoadZipCode())
        .build();

    return OrderDelivery.builder()
        .receiverInfo(receiverInfo)
        .receiverAddress(receiverAddress)
        .build();
  }

}
