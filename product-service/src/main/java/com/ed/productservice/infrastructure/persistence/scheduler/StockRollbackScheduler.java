package com.ed.productservice.infrastructure.persistence.scheduler;

import com.ed.productservice.application.service.internal.ProductInternalService;
import com.ed.productservice.domain.vo.StockDecreaseHistoryStatus;
import com.ed.productservice.infrastructure.persistence.entity.StockDecreaseHistoryEntity;
import com.ed.productservice.infrastructure.persistence.repository.StockDecreaseHistoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class StockRollbackScheduler {

    private final StockDecreaseHistoryRepository stockDecreaseHistoryRepository;
    private final ProductInternalService productInternalService;
    private final SchedulerUtil schedulerUtil;

    @Transactional
    public void rollbackUncommittedStock() {
        List<StockDecreaseHistoryEntity> uncommittedStocks = findUncommittedStocks();
        if (uncommittedStocks.isEmpty()) {
            return;
        }

        Set<String> transactionIdSet = schedulerUtil.convertSet(uncommittedStocks);

        stockRollback(transactionIdSet);

        updateStockHistoryStatus(uncommittedStocks);
    }

    private static void updateStockHistoryStatus(List<StockDecreaseHistoryEntity> uncommittedStocks) {
        for (StockDecreaseHistoryEntity entity : uncommittedStocks) {
            entity.setStatus(StockDecreaseHistoryStatus.SCHEDULING_ROLLBACK);
        }
    }

    private void stockRollback(Set<String> transactionIdSet) {
        for (String transactionId : transactionIdSet) {
            try {
                productInternalService.increaseStock(transactionId);
            } catch (Exception e) {
                log.error("재고 롤백 실패 transactionId: = {}", transactionId, e);
            }
        }
    }

    private List<StockDecreaseHistoryEntity> findUncommittedStocks() {
        LocalDateTime timeLimit = LocalDateTime.now().minusMinutes(10);

        return stockDecreaseHistoryRepository
            .findUncommittedStocks(timeLimit, StockDecreaseHistoryStatus.DECREASED);
    }
}
