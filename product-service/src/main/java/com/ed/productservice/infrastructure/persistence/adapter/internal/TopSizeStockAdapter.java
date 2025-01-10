package com.ed.productservice.infrastructure.persistence.adapter.internal;

import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.infrastructure.persistence.entity.size.TopSizeStockEntity;
import com.ed.productservice.infrastructure.persistence.repository.TopSizeStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TopSizeStockAdapter {

  private final TopSizeStockRepository topSizeStockRepository;

  public void topDecreaseStock(ProductReservationInfoDomain reservationInfo, Long productId) {
    TopSizeStockEntity topSizeStockEntity = topSizeStockRepository.findByProductIdAndTopSize(
        productId,
        reservationInfo.getSize());

    topSizeStockEntity.decreaseStock(reservationInfo.getQuantity());

  }

  public void topIncrease(ProductReservationInfoDomain item, Long productId) {
    TopSizeStockEntity entity = topSizeStockRepository.findByProductIdAndTopSize(productId,
        item.getSize());

    entity.increaseStock(item.getQuantity());
  }
}
