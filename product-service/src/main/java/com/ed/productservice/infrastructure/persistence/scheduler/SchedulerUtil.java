package com.ed.productservice.infrastructure.persistence.scheduler;

import com.ed.productservice.infrastructure.persistence.entity.StockDecreaseHistoryEntity;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SchedulerUtil {

  public Set<String> convertSet(List<StockDecreaseHistoryEntity> uncommittedStocks) {

    return uncommittedStocks.stream().map(
            StockDecreaseHistoryEntity::getTransactionId)
        .collect(Collectors.toSet());
  }
}
