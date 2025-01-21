package com.ed.orderservice.infrastructure.external.fegin.domain.product.adapter;

import com.ed.orderservice.application.port.in.ProductStockInPort;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.ProductClient;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockDecreaseResponse;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockIncreaseResponse;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockPrepareRequest;
import com.ed.orderservice.libs.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductStockAdapter implements ProductStockInPort {

  private final ProductClient productClient;

  @Override
  public ApiResponse<StockDecreaseResponse> prepareStock(StockPrepareRequest request) {
    return productClient.prepareStock(request);
  }

  @Override
  public ApiResponse<StockIncreaseResponse> rollbackStock(String transactionId) {
    return productClient.rollbackStock(transactionId);
  }
}
