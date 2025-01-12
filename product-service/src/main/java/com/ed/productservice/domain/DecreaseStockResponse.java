package com.ed.productservice.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

public record DecreaseStockResponse(
    String transactionId,
    List<ProductBrandInfo> productBrandInfoList
) {

  @Getter
  @AllArgsConstructor
  public static class ProductBrandInfo {

    private Long brandId;
    private Long productId;
  }
}
