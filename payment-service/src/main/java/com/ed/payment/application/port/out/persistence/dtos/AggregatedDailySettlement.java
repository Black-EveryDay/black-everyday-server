package com.ed.payment.application.port.out.persistence.dtos;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class AggregatedDailySettlement {

  private String brandPublicId;
  private BigDecimal totalNetRevenue;
  private BigDecimal totalDiscountAmount;
  private BigDecimal totalCommission;
}
