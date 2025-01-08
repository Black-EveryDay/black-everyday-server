package com.ed.productservice.presentation.web.request;

import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductForUpdate;
import com.ed.productservice.domain.vo.ProductStatus;

public record UpdateProductRequest(
    Long productId,
    Long brandId,
    String name,
    int quantity,
    int price,
    String description,
    String color,
    String image,
    ProductStatus status,
    ProductCategory category
) {

    public ProductForUpdate toDomain() {
        return new ProductForUpdate(
            productId,
            brandId,
            name,
            quantity,
            price,
            description,
            color,
            image,
            status,
            category
        );
    }
}
