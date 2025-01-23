package com.ed.productservice.infrastructure.persistence.scheduler;

import com.ed.productservice.infrastructure.persistence.entity.StockDecreaseHistoryEntity;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class SchedulerUtil {

  public Set<String> convertSet(List<StockDecreaseHistoryEntity> uncommittedStocks) {

    return uncommittedStocks.stream().map(
            StockDecreaseHistoryEntity::getTransactionId)
        .collect(Collectors.toSet());
  }

  public TimeRange getTimeRangeForCache() {
    return new TimeRange();
  }

  @Getter
  public static class TimeRange {

    private final String startTime;
    private final String endTime;

    private TimeRange() {
      this.startTime = ZonedDateTime.now(ZoneOffset.UTC).minusHours(1)
          .format(DateTimeFormatter.ISO_DATE_TIME);
      this.endTime = ZonedDateTime.now(ZoneOffset.UTC)
          .format(DateTimeFormatter.ISO_DATE_TIME);
    }
  }
}
