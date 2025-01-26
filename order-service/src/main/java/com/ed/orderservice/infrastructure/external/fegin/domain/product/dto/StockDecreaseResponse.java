package com.ed.orderservice.infrastructure.external.fegin.domain.product.dto;

import java.util.List;

public record StockDecreaseResponse(
    String transactionId,
    List<ProductBrandInfo> productBrandInfoList
) {}



