package com.ed.productservice.presentation.web.request;

import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.domain.ProductForCreate;

import java.math.BigDecimal;
import java.util.List;

public record BottomProductCreateRequest(
        ProductCommonInfo productInfo,
        List<BottomSizeRequest> bottomSizeRequestList

) {
    public static class BottomSizeRequest{
        private String bottomSize;
        private BigDecimal waistWidth;
        private BigDecimal hipWidth;
        private BigDecimal thighWidth;
        private BigDecimal bottomTotalLength;

        public BottomSizeRequest(String bottomSize, BigDecimal waistWidth, BigDecimal hipWidth, BigDecimal thighWidth, BigDecimal bottomTotalLength) {
            this.bottomSize = bottomSize;
            this.waistWidth = waistWidth;
            this.hipWidth = hipWidth;
            this.thighWidth = thighWidth;
            this.bottomTotalLength = bottomTotalLength;
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
                        request.bottomTotalLength
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
