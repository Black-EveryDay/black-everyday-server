package com.ed.orderservice.infrastructure.external.fegin.domain.product;

import com.ed.orderservice.infrastructure.external.fegin.domain.product.config.ProductFeignErrorDecoder;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockDecreaseResponse;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockIncreaseResponse;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockPrepareRequest;
import com.ed.orderservice.libs.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service", configuration = ProductFeignErrorDecoder.class)
public interface ProductClient {

  @PostMapping("/api/v1/products/internal/prepare")
  public ApiResponse<StockDecreaseResponse> prepareStock(
      @RequestBody StockPrepareRequest request);

  @PostMapping("/api/v1/products/internal/rollback/{transactionId}")
  public ApiResponse<StockIncreaseResponse> rollbackStock(
      @PathVariable("transactionId") String transactionId);

}


