package com.ed.productservice.presentation.web.request;

import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.domain.ProductForCreate;

public record BottomProductCreateRequest(
        ProductCommonInfo productInfo,
        String bottomSize,
        int waistWidth,
        int hipWidth,
        int thighWidth,
        int bottomTotalLength
) {
    public BottomProduct toDomain() {
        return new BottomProduct(
                createProductInfo(),
                bottomSize,
                waistWidth,
                hipWidth,
                thighWidth,
                bottomTotalLength
        );
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
