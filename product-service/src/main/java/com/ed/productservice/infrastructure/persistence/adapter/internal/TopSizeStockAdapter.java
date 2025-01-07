package com.ed.productservice.infrastructure.persistence.adapter.internal;

import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.infrastructure.persistence.entity.size.TopSizeStockEntity;
import com.ed.productservice.infrastructure.persistence.repository.TopSizeStockRepository;
import com.ed.productservice.presentation.web.request.InventoryReservationRequest.ProductReservationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TopSizeStockAdapter {

    private final TopSizeStockRepository topSizeStockRepository;

    public void topProductPrepare(ProductReservationInfo item) {
        TopSizeStockEntity topSizeStockEntity = topSizeStockRepository.findByProductIdAndTopSize(
            item.productId(), item.size());

        topSizeStockEntity.validateStockAvailability(item.quantity());

    }

    public void topDecreaseStock(ProductReservationInfoDomain reservationInfo) {
        TopSizeStockEntity topSizeStockEntity = topSizeStockRepository.findByProductIdAndTopSize(
            reservationInfo.getProductId(),
            reservationInfo.getSize());

        topSizeStockEntity.decreaseStock(reservationInfo.getQuantity());

    }


    public void topIncrease(ProductReservationInfoDomain item) {
        TopSizeStockEntity entity = topSizeStockRepository.findByProductIdAndTopSize(item.getProductId(),
            item.getSize());

        entity.increaseStock(item.getQuantity());
    }
}
