package com.ed.eventservice.coupon.adapter.in.web.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueCouponRequest {

  private UUID userId;
}
