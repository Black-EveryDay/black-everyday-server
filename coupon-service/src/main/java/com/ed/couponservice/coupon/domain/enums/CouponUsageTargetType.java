package com.ed.couponservice.coupon.domain.enums;

import java.util.Objects;
import java.util.UUID;

public enum CouponUsageTargetType {
  ALL {
    @Override
    public boolean isTargetType(UUID targetId1, UUID targetId2) {
      return true;
    }
  },
  BRAND {
    @Override
    public boolean isTargetType(UUID targetId1, UUID targetId2) {
      return Objects.equals(targetId1, targetId2);
    }
  },
  CATEGORY {
    @Override
    public boolean isTargetType(UUID targetId1, UUID targetId2) {
      return Objects.equals(targetId1, targetId2);
    }
  },
  PRODUCT {
    @Override
    public boolean isTargetType(UUID targetId1, UUID targetId2) {
      return Objects.equals(targetId1, targetId2);
    }
  },
  ;

  public abstract boolean isTargetType(UUID targetId1, UUID targetId2);

  public boolean isNotTargetType(UUID targetId1, UUID targetId2) {
    return !isTargetType(targetId1, targetId2);
  }

}
