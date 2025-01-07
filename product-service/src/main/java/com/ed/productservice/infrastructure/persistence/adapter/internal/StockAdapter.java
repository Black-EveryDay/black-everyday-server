package com.ed.productservice.infrastructure.persistence.adapter.internal;

import static com.ed.productservice.libs.common.ErrorCode.*;

import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.infrastructure.persistence.entity.BaseEntity;
import com.ed.productservice.infrastructure.persistence.entity.StockDecreaseHistoryEntity;
import com.ed.productservice.infrastructure.persistence.repository.StockDecreaseHistoryRepository;
import com.ed.productservice.libs.common.ProductException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockAdapter {

    private final StockDecreaseHistoryRepository stockDecreaseHistoryRepository;

    public void save(ProductReservationInfoDomain productReservationInfo, String reservationId) {
        stockDecreaseHistoryRepository.save(
            new StockDecreaseHistoryEntity(
                productReservationInfo.getProductId(),
                productReservationInfo.getQuantity(),
                productReservationInfo.getSize(),
                productReservationInfo.getProductCategory(),
                reservationId));
    }

    public List<ProductReservationInfoDomain> findAllByReservationId(String reservationId) {
        List<StockDecreaseHistoryEntity> entityList = stockDecreaseHistoryRepository.findAllByReservationId(
            reservationId);

        return entityList.stream()
            .map(ProductReservationInfoDomain::from)
            .toList();
    }

    public void deleteStockHistory(String reservationId) {
        stockDecreaseHistoryRepository.findAllByReservationId(
            reservationId).forEach(BaseEntity::deletedFrom);
    }

    public void isDuplicateStockDecrease(ProductReservationInfoDomain productReservationInfo, String reservationId) {
        if (!stockDecreaseHistoryRepository.findByProductIdAndSizeAndReservationId(
                productReservationInfo.getProductId(), productReservationInfo.getSize(), reservationId)
            .isEmpty()) {

            throw new ProductException(INVENTORY_ALREADY_DECREASE);
        }
    }
}
