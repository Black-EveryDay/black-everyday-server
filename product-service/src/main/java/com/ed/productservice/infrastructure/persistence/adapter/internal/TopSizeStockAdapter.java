package com.ed.productservice.infrastructure.persistence.adapter.internal;

import com.ed.productservice.application.service.internal.strategy.StockDecreaseStrategy;
import com.ed.productservice.application.service.internal.strategy.StockIncreaseStrategy;
import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.infrastructure.persistence.entity.size.TopSizeStockEntity;
import com.ed.productservice.infrastructure.persistence.repository.TopSizeStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TopSizeStockAdapter implements StockDecreaseStrategy, StockIncreaseStrategy {

  private final TopSizeStockRepository topSizeStockRepository;

  @Override
  public void decreaseStock(ProductReservationInfoDomain productReservationInfo, Long productId) {
    TopSizeStockEntity topSizeStockEntity = topSizeStockRepository.findByProductIdAndTopSize(
        productId,
        productReservationInfo.getSize());

    topSizeStockEntity.decreaseStock(productReservationInfo.getQuantity());
  }

  @Override
  public void increaseStock(ProductReservationInfoDomain productReservationInfo, Long productId) {
    TopSizeStockEntity entity = topSizeStockRepository.findByProductIdAndTopSize(productId,
        productReservationInfo.getSize());

    entity.increaseStock(productReservationInfo.getQuantity());
  }
}
