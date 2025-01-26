package com.ed.orderservice.application.port.in;

import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockDecreaseResponse;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockIncreaseResponse;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockPrepareRequest;
import com.ed.orderservice.libs.response.ApiResponse;

public interface ProductStockInPort {

  ApiResponse<StockDecreaseResponse> prepareStock(StockPrepareRequest request);

  ApiResponse<StockIncreaseResponse> rollbackStock(String transactionId);

}
