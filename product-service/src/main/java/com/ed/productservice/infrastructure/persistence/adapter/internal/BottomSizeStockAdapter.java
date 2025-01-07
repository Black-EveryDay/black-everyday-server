package com.ed.productservice.infrastructure.persistence.adapter.internal;

import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.infrastructure.persistence.entity.size.BottomSizeStockEntity;
import com.ed.productservice.infrastructure.persistence.repository.BottomSizeStockRepository;
import com.ed.productservice.presentation.web.request.InventoryReservationRequest.ProductReservationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BottomSizeStockAdapter {

    private final BottomSizeStockRepository bottomSizeStockRepository;

    public void bottomPrepare(ProductReservationInfo item) {
        BottomSizeStockEntity bottomSizeStockEntity = bottomSizeStockRepository.findByProductIdAndBottomSize(
            item.productId(), item.size());

        bottomSizeStockEntity.validateStockAvailability(item.quantity());
    }

    public void bottomDecreaseStock(ProductReservationInfoDomain reservationInfo) {
        BottomSizeStockEntity bottomSizeStockEntity = bottomSizeStockRepository.findByProductIdAndBottomSize(
            reservationInfo.getProductId(),
            reservationInfo.getSize());

        bottomSizeStockEntity.decrease(reservationInfo.getQuantity());
    }

    public void bottomIncrease(ProductReservationInfoDomain item) {
        BottomSizeStockEntity entity = bottomSizeStockRepository.findByProductIdAndBottomSize(
            item.getProductId(),
            item.getSize());

        entity.increaseStock(item.getQuantity());
    }
}
