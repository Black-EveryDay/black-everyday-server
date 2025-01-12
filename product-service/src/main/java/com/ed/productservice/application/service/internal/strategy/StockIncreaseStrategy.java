package com.ed.productservice.application.service.internal.strategy;

import com.ed.productservice.domain.vo.ProductReservationInfoDomain;

public interface StockIncreaseStrategy {
  void increaseStock(ProductReservationInfoDomain productReservationInfo, Long productId);

}
