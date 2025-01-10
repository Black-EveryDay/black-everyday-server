package com.ed.productservice.infrastructure.persistence.scheduler;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DistributedStockRollback {

  private final RedissonClient redissonClient;
  private final StockRollbackScheduler stockRollbackScheduler;

  public DistributedStockRollback(RedissonClient redissonClient,
      StockRollbackScheduler stockRollbackScheduler) {
    this.redissonClient = redissonClient;
    this.stockRollbackScheduler = stockRollbackScheduler;
  }

  @Scheduled(cron = "30 * * * * *")
  public void rollback() {
    RLock lock = redissonClient.getLock("stock-rollback-lock");
    try {
      boolean isLocked = lock.tryLock(10, 10, TimeUnit.SECONDS);

      if (isLocked) {
        try {
          stockRollbackScheduler.rollbackUncommittedStock();
        } finally {
          lock.unlock();
        }
      }
    } catch (InterruptedException e) {
      log.error("Interrupted while acquiring lock for stock rollback.", e);
    }
  }
}
