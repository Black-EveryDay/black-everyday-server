package com.ed.payment.application.port.out.persistence;

import com.ed.payment.application.port.out.persistence.dtos.AggregatedDailySettlement;
import java.util.List;

public interface CreateMonthlySettlementPort {
  void bulkCreateMonthlySettlement(List<AggregatedDailySettlement> aggregatedDailySettlementRespons);
}
