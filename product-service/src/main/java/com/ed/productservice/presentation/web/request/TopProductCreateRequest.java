package com.ed.productservice.presentation.web.request;

import com.ed.productservice.domain.ProductForCreate;
import com.ed.productservice.domain.TopProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record TopProductCreateRequest(
        ProductCommonInfo productInfo,
        List<TopSizeRequest> topSizeRequestList
) {
    @Getter
    @NoArgsConstructor
    public static class TopSizeRequest {
        private String topSize;
        private BigDecimal totalLength;
        private BigDecimal shoulderWidth;
        private BigDecimal chestWidth;
        private BigDecimal sleeveLength;
        private Integer quantity;

        public TopSizeRequest(String topSize, BigDecimal totalLength, BigDecimal shoulderWidth,
            BigDecimal chestWidth, BigDecimal sleeveLength, Integer quantity) {
            this.topSize = topSize;
            this.totalLength = totalLength;
            this.shoulderWidth = shoulderWidth;
            this.chestWidth = chestWidth;
            this.sleeveLength = sleeveLength;
            this.quantity = quantity;
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
                        request.sleeveLength,
                        request.quantity
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
