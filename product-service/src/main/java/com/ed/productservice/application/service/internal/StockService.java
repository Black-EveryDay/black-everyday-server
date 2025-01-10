package com.ed.productservice.application.service.internal;

import static com.ed.productservice.libs.common.ErrorCode.STOCK_RESERVATION_NOT_FOUND;

import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.infrastructure.persistence.adapter.ProductAdapter;
import com.ed.productservice.infrastructure.persistence.adapter.internal.BottomSizeStockAdapter;
import com.ed.productservice.infrastructure.persistence.adapter.internal.TopSizeStockAdapter;
import com.ed.productservice.infrastructure.persistence.adapter.internal.StockAdapter;
import com.ed.productservice.libs.common.ProductException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockService {

  private final TopSizeStockAdapter topSizeStockAdapter;
  private final BottomSizeStockAdapter bottomSizeStockAdapter;
  private final StockAdapter stockAdapter;
  private final ProductAdapter productAdapter;

  public void decreaseStockReservation(ProductReservationInfoDomain productReservationInfo,
      String transactionId, Long productId) {
    stockAdapter.isDuplicateStockDecrease(productReservationInfo, transactionId);

    ProductCategory category = productReservationInfo.getProductCategory();
    if (category.equals(ProductCategory.TOP)) {
      topSizeStockAdapter.topDecreaseStock(productReservationInfo, productId);
    }

    if (category.equals(ProductCategory.BOTTOM)) {
      bottomSizeStockAdapter.bottomDecreaseStock(productReservationInfo, productId);
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
      if (item.getProductCategory().equals(ProductCategory.TOP)) {
        topSizeStockAdapter.topIncrease(item, product.getProductId());
      }

      if (item.getProductCategory().equals(ProductCategory.BOTTOM)) {
        bottomSizeStockAdapter.bottomIncrease(item, product.getProductId());
      }

      stockAdapter.deleteStockHistory(transactionId);
    }
  }
}
