package com.ed.productservice.infrastructure.persistence.adapter.internal;

import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.infrastructure.persistence.entity.size.BottomSizeStockEntity;
import com.ed.productservice.infrastructure.persistence.entity.size.TopSizeStockEntity;
import com.ed.productservice.infrastructure.persistence.repository.BottomSizeRepository;
import com.ed.productservice.presentation.web.request.InventoryReservationRequest.ProductReservationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BottomSizeStockAdapter {

    private final BottomSizeRepository bottomSizeRepository;

    public void bottomPrepare(ProductReservationInfo item) {
        BottomSizeStockEntity bottomSizeStockEntity = bottomSizeRepository.findByProductIdAndBottomSize(
            item.productId(), item.size());

        bottomSizeStockEntity.validateStockAvailability(item.quantity());
    }

    public void bottomDecreaseStock(ProductReservationInfoDomain reservationInfo) {
        BottomSizeStockEntity bottomSizeStockEntity = bottomSizeRepository.findByProductIdAndBottomSize(
            reservationInfo.getProductId(),
            reservationInfo.getSize());

        bottomSizeStockEntity.decrease(reservationInfo.getQuantity());
    }

    public void bottomIncrease(ProductReservationInfoDomain item) {
        BottomSizeStockEntity entity = bottomSizeRepository.findByProductIdAndBottomSize(
            item.getProductId(),
            item.getSize());

        entity.increaseStock(item.getQuantity());
    }
}
