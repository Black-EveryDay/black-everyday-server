package com.ed.payment.application.port.out.persistence;

import com.ed.payment.application.port.out.persistence.dtos.AggregatedDailySettlement;
import java.time.LocalDateTime;
import java.util.List;

public interface GetDailySettlementPort {
  List<AggregatedDailySettlement> aggregateLastMonthDailySettlements(LocalDateTime startDateTime, LocalDateTime endDateTime, String currentId, int pageSize);
}
