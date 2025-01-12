package com.ed.productservice.presentation.web.request;

import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
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

  public List<ProductReservationInfoDomain> toDomain() {
    return items.stream()
        .map(productReservationInfo -> new ProductReservationInfoDomain(
            productReservationInfo.productPublicId,
            productReservationInfo.quantity,
            productReservationInfo.size,
            productReservationInfo.productCategory
        )).toList();
  }
}