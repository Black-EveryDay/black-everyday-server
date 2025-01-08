package com.ed.productservice.application.service.internal;

import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ProductInternalService {
    private final StockService stockService;

    @Transactional
    public String decreaseStock(List<ProductReservationInfoDomain> request) {
        String transactionId = UUID.randomUUID().toString();

        for (ProductReservationInfoDomain item : request) {
            stockService.decreaseStockReservation(item, transactionId);
        }

        return transactionId;
    }

    @Transactional
    public String increaseStock(String transactionId) {
        stockService.increaseStock(transactionId);

        return transactionId;
    }
}