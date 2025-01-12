package com.ed.productservice.domain.vo;

public record ProductInfoDto(
    String productPublicId,
    String color,
    String image,
    ProductCategory category,
    String description,
    String name,
    Integer price,
    String brandName
) {

}