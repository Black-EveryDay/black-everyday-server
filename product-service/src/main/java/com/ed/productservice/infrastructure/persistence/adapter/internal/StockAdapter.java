package com.ed.productservice.infrastructure.persistence.adapter.internal;

import static com.ed.productservice.libs.common.ErrorCode.*;

import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.domain.vo.StockDecreaseHistoryStatus;
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
            productReservationInfo.getProductPublicId(),
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
      history.delete();
      history.setStatus(StockDecreaseHistoryStatus.ROLLBACK);
    });
  }

  public void isDuplicateStockDecrease(ProductReservationInfoDomain productReservationInfo,
      String transactionId) {
    if (!stockDecreaseHistoryRepository.findByProductIdAndSizeAndTransactionId(
            productReservationInfo.getProductPublicId(), productReservationInfo.getSize(),
            transactionId)
        .isEmpty()) {

      throw new ProductException(INVENTORY_ALREADY_DECREASE);
    }
  }

  public String commitStock(String transactionId) {
    List<StockDecreaseHistoryEntity> entityList = stockDecreaseHistoryRepository.findAllByTransactionId(
        transactionId);

    if (entityList.isEmpty()) {
      throw new ProductException(STOCK_RESERVATION_NOT_FOUND);
    }

    for (StockDecreaseHistoryEntity entity : entityList) {
      entity.setStatus(StockDecreaseHistoryStatus.COMMITTED);
    }

    return transactionId;
  }
}
