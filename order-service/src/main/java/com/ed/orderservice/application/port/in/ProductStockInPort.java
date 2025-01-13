package com.ed.orderservice.application.port.in;

import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockDecreaseResponse;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockIncreaseResponse;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockPrepareRequest;

public interface ProductStockInPort {

  StockDecreaseResponse prepareStock(StockPrepareRequest request);

  StockIncreaseResponse rollbackStock(String transactionId);

}
