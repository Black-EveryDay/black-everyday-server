package com.ed.productservice.infrastructure.persistence.adapter.internal;

import static com.ed.productservice.libs.common.ErrorCode.*;

import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.domain.vo.StockDecreaseHistoryStatus;
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

    public void save(ProductReservationInfoDomain productReservationInfo, String transactionId) {
        stockDecreaseHistoryRepository.save(
            new StockDecreaseHistoryEntity(
                productReservationInfo.getProductId(),
                productReservationInfo.getQuantity(),
                productReservationInfo.getSize(),
                productReservationInfo.getProductCategory(),
                transactionId,
                StockDecreaseHistoryStatus.DECREASED));
    }

    public List<ProductReservationInfoDomain> findAllByTransactionId(String transactionId) {
        List<StockDecreaseHistoryEntity> entityList = stockDecreaseHistoryRepository.findAllByTransactionId(
            transactionId);

        return entityList.stream()
            .map(ProductReservationInfoDomain::from)
            .toList();
    }

    public void deleteStockHistory(String transactionId) {
        stockDecreaseHistoryRepository.findAllByTransactionId(
            transactionId).forEach(history -> {
            history.deletedFrom();
            history.setStatus(StockDecreaseHistoryStatus.ROLLBACK);
        });
    }

    public void isDuplicateStockDecrease(ProductReservationInfoDomain productReservationInfo, String transactionId) {
        if (!stockDecreaseHistoryRepository.findByProductIdAndSizeAndTransactionId(
                productReservationInfo.getProductId(), productReservationInfo.getSize(), transactionId)
            .isEmpty()) {

            throw new ProductException(INVENTORY_ALREADY_DECREASE);
        }
    }
}
