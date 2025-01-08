package com.ed.productservice.presentation.web.response;

import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductInfoDto;


public record ProductDetailResponse(
    Long productId,
    String color,
    String image,
    ProductCategory category,
    String description,
    String name,
    Integer price,
    String brandName
) {

    public static ProductDetailResponse from(ProductInfoDto dto) {
        return new ProductDetailResponse(
            dto.productId(),
            dto.color(),
            dto.image(),
            dto.category(),
            dto.description(),
            dto.name(),
            dto.price(),
            dto.brandName()
        );
    }
}
