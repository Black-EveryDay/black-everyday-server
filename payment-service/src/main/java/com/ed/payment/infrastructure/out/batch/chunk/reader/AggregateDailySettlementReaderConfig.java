package com.ed.payment.infrastructure.out.batch.chunk.reader;

import com.ed.payment.application.port.out.persistence.GetDailySettlementPort;
import com.ed.payment.application.port.out.persistence.dtos.AggregatedDailySettlement;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AggregateDailySettlementReaderConfig {

  private static final int DEFAULT_PAGE_SIZE = 3000;
  private final LocalDate today = LocalDate.now();

  @Bean
  public AbstractPagingItemReader<AggregatedDailySettlement> aggregateDailySettlementItemReader(
      EntityManagerFactory emf, GetDailySettlementPort getDailySettlementPort) {
    return new AggregateDailySettlementCustomItemReader(emf, getDailySettlementPort,
        getStartDateTime(), getEndDateTime(), DEFAULT_PAGE_SIZE);
  }

  public LocalDateTime getStartDateTime() {
    return LocalDateTime.of(getFirstDayOfLastMonth(), LocalTime.MIN);
  }

  public LocalDateTime getEndDateTime() {
    return LocalDateTime.of(getLastDayOfLastMonth(), LocalTime.MAX);
  }

  private LocalDate getFirstDayOfLastMonth() {
    return today.minusMonths(1).withDayOfMonth(1);
  }

  private LocalDate getLastDayOfLastMonth() {
    return today.withDayOfMonth(1).minusDays(1);
  }
}
