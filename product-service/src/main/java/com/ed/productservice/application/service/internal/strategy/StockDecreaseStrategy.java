package com.ed.productservice.application.service.internal.strategy;

import com.ed.productservice.domain.vo.ProductReservationInfoDomain;

public interface StockDecreaseStrategy {
  void decreaseStock(ProductReservationInfoDomain productReservationInfo, Long productId);

}
