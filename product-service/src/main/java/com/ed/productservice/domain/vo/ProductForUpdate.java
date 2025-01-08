package com.ed.productservice.domain.vo;


public record ProductForUpdate(
     String productPublicId,
     Long brandId,
     String name,
     int price,
     String description,
     String color,
     String image,
     ProductStatus status,
     ProductCategory category
) {

}
