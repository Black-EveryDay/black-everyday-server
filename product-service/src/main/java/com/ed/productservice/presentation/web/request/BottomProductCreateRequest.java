package com.ed.productservice.presentation.web.request;

import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.domain.ProductForCreate;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

public record BottomProductCreateRequest(
        ProductCommonInfo productInfo,
        List<BottomSizeRequest> bottomSizeRequestList

) {
    @Getter
    @NoArgsConstructor
    public static class BottomSizeRequest{
        private String bottomSize;
        private BigDecimal waistWidth;
        private BigDecimal hipWidth;
        private BigDecimal thighWidth;
        private BigDecimal bottomTotalLength;
        private Integer quantity;

        public BottomSizeRequest(String bottomSize, BigDecimal waistWidth, BigDecimal hipWidth,
            BigDecimal thighWidth, BigDecimal bottomTotalLength, Integer quantity) {
            this.bottomSize = bottomSize;
            this.waistWidth = waistWidth;
            this.hipWidth = hipWidth;
            this.thighWidth = thighWidth;
            this.bottomTotalLength = bottomTotalLength;
            this.quantity = quantity;
        }
    }

    public BottomProduct toDomain() {
        return new BottomProduct(
                createProductInfo(),
                createBottomSizeList()

        );
    }

    private List<BottomProduct.BottomSize> createBottomSizeList() {
        return bottomSizeRequestList.stream()
                .map(request -> new BottomProduct.BottomSize(
                        request.bottomSize,
                        request.waistWidth,
                        request.hipWidth,
                        request.thighWidth,
                        request.bottomTotalLength,
                        request.quantity
                ))
                .toList();
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
