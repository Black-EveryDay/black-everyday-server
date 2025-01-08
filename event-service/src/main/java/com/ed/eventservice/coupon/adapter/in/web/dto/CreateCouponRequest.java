package com.ed.eventservice.coupon.adapter.in.web.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCouponRequest {

  private Integer quantity;
}
