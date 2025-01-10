package com.ed.productservice.application.service.internal;

import static com.ed.productservice.libs.common.ErrorCode.STOCK_RESERVATION_NOT_FOUND;

import com.ed.productservice.application.service.internal.strategy.StockDecreaseStrategy;
import com.ed.productservice.application.service.internal.strategy.StockIncreaseStrategy;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.infrastructure.persistence.adapter.ProductAdapter;
import com.ed.productservice.infrastructure.persistence.adapter.internal.StockAdapter;
import com.ed.productservice.libs.common.ProductException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockService {

  private final StockAdapter stockAdapter;
  private final ProductAdapter productAdapter;
  private final Map<ProductCategory, StockDecreaseStrategy> decreaseStockStrategy;
  private final Map<ProductCategory, StockIncreaseStrategy> increaseStockStrategy;

  public void decreaseStockReservation(ProductReservationInfoDomain productReservationInfo,
      String transactionId, Long productId) {
    stockAdapter.isDuplicateStockDecrease(productReservationInfo, transactionId);

    ProductCategory category = productReservationInfo.getProductCategory();
    StockDecreaseStrategy strategy = decreaseStockStrategy.get(category);

    if (strategy != null) {
      strategy.decreaseStock(productReservationInfo, productId);
    } else {
      throw new IllegalArgumentException("존재하지 않는 카테고리 입니다. " + category);
    }

    stockAdapter.save(productReservationInfo, transactionId);
  }

  public void increaseStock(String transactionId) {
    List<ProductReservationInfoDomain> itemList = stockAdapter.findAllByTransactionId(
        transactionId);

    if (itemList.isEmpty()) {
      throw new ProductException(STOCK_RESERVATION_NOT_FOUND);
    }

    for (ProductReservationInfoDomain item : itemList) {
      Product product = productAdapter.findOne(item.getProductPublicId());
      StockIncreaseStrategy strategy = increaseStockStrategy.get(product.getCategory());

      if (strategy != null) {
        strategy.increaseStock(item, product.getProductId());
      } else {
        throw new IllegalArgumentException("존재하지 않는 카테고리 입니다. " + product.getCategory());
      }

      stockAdapter.deleteStockHistory(transactionId);
    }
  }
}
