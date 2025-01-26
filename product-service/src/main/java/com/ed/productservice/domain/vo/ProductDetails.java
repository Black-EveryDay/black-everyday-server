package com.ed.productservice.domain.vo;

public record ProductDetails(
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