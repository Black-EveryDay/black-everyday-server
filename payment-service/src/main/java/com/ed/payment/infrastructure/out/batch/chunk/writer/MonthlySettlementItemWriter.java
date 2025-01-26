package com.ed.payment.infrastructure.out.batch.chunk.writer;

import com.ed.payment.application.port.out.persistence.CreateMonthlySettlementPort;
import com.ed.payment.application.port.out.persistence.dtos.AggregatedDailySettlement;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonthlySettlementItemWriter implements ItemWriter<AggregatedDailySettlement> {

  private final CreateMonthlySettlementPort createMonthlySettlementPort;

  @Override
  public void write(Chunk<? extends AggregatedDailySettlement> chunk) {
    createMonthlySettlementPort.bulkCreateMonthlySettlement(new ArrayList<>(chunk.getItems()));
  }
}
