package com.ed.productservice.presentation.web.internal;

import com.ed.productservice.application.service.internal.ProductInternalService;
import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.presentation.web.request.StockPrepareRequest;
import com.ed.productservice.presentation.web.response.ProductReservationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/products/internal")
@RestController
public class ProductInternalController {

    private final ProductInternalService productInternalService;

    @PostMapping("/prepare")
    public ProductReservationResponse prepareStock(@RequestBody StockPrepareRequest request) {
        List<ProductReservationInfoDomain> domain = request.toDomain();

        String transactionId = productInternalService.decreaseStock(domain);

        return ProductReservationResponse.from(transactionId);
    }

    @PostMapping("/rollback/{transactionId}")
    public ProductReservationResponse rollbackStock(
        @PathVariable("transactionId") String transactionId) {

        return ProductReservationResponse.from(productInternalService.increaseStock(transactionId));
    }
}
