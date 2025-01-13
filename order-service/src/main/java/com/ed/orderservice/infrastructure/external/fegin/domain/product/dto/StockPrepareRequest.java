package com.ed.orderservice.infrastructure.external.fegin.domain.product.dto;

import java.util.List;

public record StockPrepareRequest(
    List<ProductReservationInfo> items
) {

  public record ProductReservationInfo(
      String productPublicId,
      int quantity,
      String size,
      ProductCategory productCategory
  ) {

  }
}
