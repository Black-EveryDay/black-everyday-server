package com.ed.orderservice.domain.vo.receiver;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReceiverAddress {
  private String address;
  private String zipCode;
  private String roadZipCode;


  @Builder
  private ReceiverAddress(String address, String zipCode, String roadZipCode
  ) {
    this.address = address;
    this.zipCode = zipCode;
    this.roadZipCode = roadZipCode;
  }
}
