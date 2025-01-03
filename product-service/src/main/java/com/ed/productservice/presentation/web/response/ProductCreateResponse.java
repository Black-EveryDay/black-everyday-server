package com.ed.productservice.presentation.web.response;

import com.ed.productservice.domain.vo.Product;

import java.time.LocalDateTime;

public record ProductCreateResponse(
        String productPublicId,
        LocalDateTime createdAt
) {

    public static ProductCreateResponse from(Product product) {
        return new ProductCreateResponse(
                product.getProductPublicId(),
                product.getCreatedAt()
        );
    }
}
