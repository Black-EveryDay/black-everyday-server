package com.ed.productservice.infrastructure.persistence.scheduler;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DistributedLockScheduler {

  private final RedissonClient redissonClient;
  private final StockRollbackScheduler stockRollbackScheduler;
  private final PopularProductCacheAdapter popularProductCacheAdapter;

  @Scheduled(cron = "30 * * * * *")
  public void scheduleDistributedStockRollback() {
    RLock lock = redissonClient.getLock("stock-rollback-lock");
    try {
      boolean isLocked = lock.tryLock(1, 5, TimeUnit.SECONDS);

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

  @Scheduled(cron = "0 0 */1 * * *")
  public void schedulerProductCache() {
    RLock lock = redissonClient.getLock("popular-products-cache-lock");
    try {
      boolean isLocked = lock.tryLock(1, 5, TimeUnit.SECONDS);

      if (isLocked) {
        try {
          popularProductCacheAdapter.refreshPopularProductsCache();
        } finally {
          lock.unlock();
        }
      }
    } catch (InterruptedException e) {
      log.error("Failed to acquire lock for popular products caching", e);
    }
  }
}
