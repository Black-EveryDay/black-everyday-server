package com.ed.productservice.application.config;

import com.ed.productservice.application.service.internal.strategy.StockDecreaseStrategy;
import com.ed.productservice.application.service.internal.strategy.StockIncreaseStrategy;
import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.infrastructure.persistence.adapter.internal.BottomSizeStockAdapter;
import com.ed.productservice.infrastructure.persistence.adapter.internal.TopSizeStockAdapter;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockServiceConfig {
  @Bean
  public Map<ProductCategory, StockDecreaseStrategy> decreaseStockStrategy(
      TopSizeStockAdapter topSizeStockAdapter,
      BottomSizeStockAdapter bottomSizeStockAdapter) {

    Map<ProductCategory, StockDecreaseStrategy> strategyMap = new EnumMap<>(ProductCategory.class);
    strategyMap.put(ProductCategory.TOP, topSizeStockAdapter);
    strategyMap.put(ProductCategory.BOTTOM, bottomSizeStockAdapter);

    return strategyMap;
  }

  @Bean
  public Map<ProductCategory, StockIncreaseStrategy> increaseStockStrategy(
      TopSizeStockAdapter topSizeStockAdapter,
      BottomSizeStockAdapter bottomSizeStockAdapter) {

    Map<ProductCategory, StockIncreaseStrategy> strategyMap = new EnumMap<>(ProductCategory.class);
    strategyMap.put(ProductCategory.TOP, topSizeStockAdapter);
    strategyMap.put(ProductCategory.BOTTOM, bottomSizeStockAdapter);

    return strategyMap;
  }
}
