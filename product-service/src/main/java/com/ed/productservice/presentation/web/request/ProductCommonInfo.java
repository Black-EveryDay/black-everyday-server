package com.ed.productservice.presentation.web.request;

import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductStatus;

public record ProductCommonInfo(
        Long brandId,
        String name,
        int price,
        String description,
        ProductStatus status,
        ProductCategory category,
        String color,
        String image,
        int quantity
) {
}
