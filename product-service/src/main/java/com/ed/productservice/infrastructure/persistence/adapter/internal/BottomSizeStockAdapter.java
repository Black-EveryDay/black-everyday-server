package com.ed.productservice.infrastructure.persistence.adapter.internal;

import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.infrastructure.persistence.entity.size.BottomSizeStockEntity;
import com.ed.productservice.infrastructure.persistence.repository.BottomSizeStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BottomSizeStockAdapter {

    private final BottomSizeStockRepository bottomSizeStockRepository;

    public void bottomDecreaseStock(ProductReservationInfoDomain reservationInfo, Long productId) {
        BottomSizeStockEntity bottomSizeStockEntity = bottomSizeStockRepository.findByProductIdAndBottomSize(
            productId,
            reservationInfo.getSize());

        bottomSizeStockEntity.decrease(reservationInfo.getQuantity());
    }

    public void bottomIncrease(ProductReservationInfoDomain item, Long productId) {
        BottomSizeStockEntity entity = bottomSizeStockRepository.findByProductIdAndBottomSize(
            productId,
            item.getSize());

        entity.increaseStock(item.getQuantity());
    }
}
