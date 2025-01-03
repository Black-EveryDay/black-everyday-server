package com.ed.productservice.presentation.web.request;

import com.ed.productservice.domain.ProductForCreate;
import com.ed.productservice.domain.TopProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record TopProductCreateRequest(
        ProductCommonInfo productInfo,
        List<TopSizeRequest> topSizeRequestList
) {
    public static class TopSizeRequest {
        private String topSize;
        private BigDecimal totalLength;
        private BigDecimal shoulderWidth;
        private BigDecimal chestWidth;
        private BigDecimal sleeveLength;

        public TopSizeRequest(String topSize, BigDecimal totalLength, BigDecimal shoulderWidth, BigDecimal chestWidth, BigDecimal sleeveLength) {
            this.topSize = topSize;
            this.totalLength = totalLength;
            this.shoulderWidth = shoulderWidth;
            this.chestWidth = chestWidth;
            this.sleeveLength = sleeveLength;
        }
    }

    public TopProduct toDomain() {
        return new TopProduct(
                createProductInfo(),
                createTopSizeList()
        );
    }

    private List<TopProduct.TopSize> createTopSizeList() {
        return topSizeRequestList.stream()
                .map(request -> new TopProduct.TopSize(
                        request.topSize,
                        request.totalLength,
                        request.shoulderWidth,
                        request.chestWidth,
                        request.sleeveLength
                ))
                .collect(Collectors.toList());
    }

    private ProductForCreate createProductInfo() {
        return new ProductForCreate(
                productInfo.brandId(),
                productInfo.name(),
                productInfo.price(),
                productInfo.description(),
                productInfo.status(),
                productInfo.category(),
                productInfo.color(),
                productInfo.quantity(),
                productInfo.image()
        );
    }
}
