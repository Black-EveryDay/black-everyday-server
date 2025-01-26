package com.ed.payment.application.port.out.persistence;

import com.ed.payment.domain.DailySettlement;
import java.util.List;

public interface CreateDailySettlementPort {
  void bulkCreateDailySettlement(List<DailySettlement> settlementSummaries);
}
