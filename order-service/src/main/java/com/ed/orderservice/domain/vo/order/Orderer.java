package com.ed.orderservice.domain.vo.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Orderer {
  private String name;
  private String phoneNumber;

  @Builder
  public Orderer(String name, String phoneNumber) {
    this.name = name;
    this.phoneNumber = phoneNumber;
  }
}
