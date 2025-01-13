package com.ed.orderservice.infrastructure.external.fegin.domain.product;

import com.ed.orderservice.infrastructure.external.fegin.config.FeignClientConfig;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockDecreaseResponse;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockIncreaseResponse;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockPrepareRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service", configuration = FeignClientConfig.class)
public interface ProductClient {

  @PostMapping("/api/v1/products/internal/prepare")
  public StockDecreaseResponse prepareStock(
      @RequestBody StockPrepareRequest request);

  @PostMapping("/api/v1/products/internal/rollback/{transactionId}")
  public StockIncreaseResponse rollbackStock(
      @PathVariable("transactionId") String transactionId);

}


