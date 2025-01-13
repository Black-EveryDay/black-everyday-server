package com.ed.orderservice.presentaion.web.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderDeliveryRequest {
  private final String receiverName;
  private final String receiverAddress;
  private final String receiverPhoneNumber;
  private final String receiverMobileNumber;
  private final String zipcode;
  private final String roadZipCode;
  private final String requirement;

  @Builder
  private OrderDeliveryRequest(String receiverName, String receiverAddress,
      String receiverPhoneNumber,
      String receiverMobileNumber, String zipcode, String roadZipCode, String requirement) {
    this.receiverName = receiverName;
    this.receiverAddress = receiverAddress;
    this.receiverPhoneNumber = receiverPhoneNumber;
    this.receiverMobileNumber = receiverMobileNumber;
    this.zipcode = zipcode;
    this.roadZipCode = roadZipCode;
    this.requirement = requirement;
  }
}
