package com.ed.productservice.infrastructure.persistence.scheduler;

import com.ed.productservice.domain.vo.ProductDetails;
import com.ed.productservice.infrastructure.persistence.elasticsearch.LogDetails;
import com.ed.productservice.infrastructure.persistence.repository.ProductRepository;
import com.ed.productservice.infrastructure.persistence.repository.elasticsearch.LogDetailsRepository;
import com.ed.productservice.infrastructure.persistence.scheduler.SchedulerUtil.TimeRange;
import com.ed.productservice.libs.common.ErrorCode;
import com.ed.productservice.libs.common.ProductException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class PopularProductCacheAdapter {

  private final LogDetailsRepository logDetailsRepository;
  private final SchedulerUtil schedulerUtil;
  private final ValueOperations<String, ProductDetails> productOperations;
  private final ProductRepository productRepository;

  @Transactional
  public void refreshPopularProductsCache() {
    TimeRange timeRangeForCache = schedulerUtil.getTimeRangeForCache();

    List<LogDetails> logDetailsList = getLogsInTimeRange(timeRangeForCache);

    Map<String, Integer> productIdAndViewCount = calculateProductViewCounts(logDetailsList);

    saveCacheProduct(productIdAndViewCount);
  }

  private List<LogDetails> getLogsInTimeRange(TimeRange timeRangeForCacheProduct) {

    return logDetailsRepository.findByTimestampBetween(
        timeRangeForCacheProduct.getStartTime(), timeRangeForCacheProduct.getEndTime());
  }


  private static Map<String, Integer> calculateProductViewCounts(List<LogDetails> logDetailsList) {
    Map<String, Integer> result = new HashMap<>();

    logDetailsList.stream()
        .filter(log -> log.getMessage().contains("publicId ="))
        .map(log -> log.getMessage().split("=")[1].trim())
        .forEach(id -> result.merge(id, 1, Integer::sum));

    return result;
  }

  private void saveCacheProduct(Map<String, Integer> productIdAndViewCount) {
    productIdAndViewCount.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .limit(10)
        .map(Map.Entry::getKey)
        .forEach(this::saveCacheProduct);
  }

  private void saveCacheProduct(String productPublicId) {
    ProductDetails productDetails = null;
    try {
      productDetails = getProductDetailsForSaveCache(productPublicId);
    } catch (Exception e) {
      log.error("Not found productPublicId: " + productPublicId);
    }

    productOperations.set("getProduct:" + productPublicId, productDetails, Duration.ofHours(1));
  }

  private ProductDetails getProductDetailsForSaveCache(String productPublicId) {

    return productRepository.findByProductAndCurrentPrice(productPublicId)
        .orElseThrow(() -> new ProductException(
            ErrorCode.PRODUCT_NOT_FOUND));
  }
}