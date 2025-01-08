package com.ed.productservice.presentation.web.request;

import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductForUpdate;
import com.ed.productservice.domain.vo.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateProductRequest(
    String productPublicId,
    Long brandId,
    @NotBlank(message = "상품명은 필수값 입니다")
    String name,
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다")
    int price,
    String description,
    @NotBlank(message = "색상은 필수값 입니다")
    String color,
    String image,
    ProductStatus status,
    ProductCategory category
) {

    public ProductForUpdate toDomain() {
        return new ProductForUpdate(
            productPublicId,
            brandId,
            name,
            price,
            description,
            color,
            image,
            status,
            category
        );
    }
}
