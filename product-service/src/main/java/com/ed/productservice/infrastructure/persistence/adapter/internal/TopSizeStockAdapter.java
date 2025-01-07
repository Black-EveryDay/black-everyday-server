package com.ed.productservice.infrastructure.persistence.adapter.internal;

import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.infrastructure.persistence.entity.size.TopSizeStockEntity;
import com.ed.productservice.infrastructure.persistence.repository.TopSizeRepository;
import com.ed.productservice.presentation.web.request.InventoryReservationRequest.ProductReservationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TopSizeStockAdapter {

    private final TopSizeRepository topSizeRepository;

    public void topProductPrepare(ProductReservationInfo item) {
        TopSizeStockEntity topSizeStockEntity = topSizeRepository.findByProductIdAndTopSize(
            item.productId(), item.size());

        topSizeStockEntity.validateStockAvailability(item.quantity());

    }

    public void topDecreaseStock(ProductReservationInfoDomain reservationInfo) {
        TopSizeStockEntity topSizeStockEntity = topSizeRepository.findByProductIdAndTopSize(
            reservationInfo.getProductId(),
            reservationInfo.getSize());

        topSizeStockEntity.decreaseStock(reservationInfo.getQuantity());

    }


    public void topIncrease(ProductReservationInfoDomain item) {
        TopSizeStockEntity entity = topSizeRepository.findByProductIdAndTopSize(item.getProductId(),
            item.getSize());

        entity.increaseStock(item.getQuantity());
    }
}
