package com.ed.orderservice.domain.vo.receiver;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReceiverInfo {
  private String name;
  private String phoneNumber;
  private String mobileNumber;
  private String requirement;

  @Builder
  private ReceiverInfo(String name, String phoneNumber, String mobileNumber, String requirement) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.mobileNumber = mobileNumber;
    this.requirement = requirement;
  }
}

