package com.ed.orderservice.application.service;

import com.ed.orderservice.application.port.in.ProductStockInPort;
import com.ed.orderservice.application.port.in.command.CreateOrderCommand;
import com.ed.orderservice.domain.mapper.OrderItemMapper;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockDecreaseResponse;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.StockPrepareRequest;
import com.ed.orderservice.libs.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductStockService {

  private final ProductStockInPort productStockInPort;
  private final OrderItemMapper orderItemMapper;

  public String reserveProductStock(CreateOrderCommand command) {
    StockPrepareRequest request = orderItemMapper.toStockPrepareRequest(command.getOrderItemDtos());
    ApiResponse<StockDecreaseResponse> response = productStockInPort.prepareStock(request);

    return response.getBody().transactionId();
  }
}
