package com.ed.productservice.infrastructure.persistence.adapter.internal;

import com.ed.productservice.application.service.internal.strategy.StockDecreaseStrategy;
import com.ed.productservice.application.service.internal.strategy.StockIncreaseStrategy;
import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.infrastructure.persistence.entity.size.BottomSizeStockEntity;
import com.ed.productservice.infrastructure.persistence.repository.BottomSizeStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BottomSizeStockAdapter implements StockDecreaseStrategy, StockIncreaseStrategy {

  private final BottomSizeStockRepository bottomSizeStockRepository;

  @Override
  public void decreaseStock(ProductReservationInfoDomain productReservationInfo, Long productId) {
    BottomSizeStockEntity bottomSizeStockEntity = bottomSizeStockRepository.findByProductIdAndBottomSize(
        productId,
        productReservationInfo.getSize());

    bottomSizeStockEntity.decrease(productReservationInfo.getQuantity());
  }

  @Override
  public void increaseStock(ProductReservationInfoDomain productReservationInfo, Long productId) {
    BottomSizeStockEntity entity = bottomSizeStockRepository.findByProductIdAndBottomSize(
        productId,
        productReservationInfo.getSize());

    entity.increaseStock(productReservationInfo.getQuantity());
  }
}
