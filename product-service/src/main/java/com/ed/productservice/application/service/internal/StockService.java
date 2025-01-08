package com.ed.productservice.application.service.internal;

import static com.ed.productservice.libs.common.ErrorCode.STOCK_RESERVATION_NOT_FOUND;

import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
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

    public void decreaseStockReservation(ProductReservationInfoDomain productReservationInfo,
        String transactionId) {
        stockAdapter.isDuplicateStockDecrease(productReservationInfo, transactionId);

        ProductCategory category = productReservationInfo.getProductCategory();
        if (category.equals(ProductCategory.TOP)) {
            topSizeStockAdapter.topDecreaseStock(productReservationInfo);
        }

        if (category.equals(ProductCategory.BOTTOM)) {
            bottomSizeStockAdapter.bottomDecreaseStock(productReservationInfo);
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
            if (item.getProductCategory().equals(ProductCategory.TOP)) {
                topSizeStockAdapter.topIncrease(item);
            }

            if (item.getProductCategory().equals(ProductCategory.BOTTOM)) {
                bottomSizeStockAdapter.bottomIncrease(item);
            }

            stockAdapter.deleteStockHistory(transactionId);
        }
    }
}
