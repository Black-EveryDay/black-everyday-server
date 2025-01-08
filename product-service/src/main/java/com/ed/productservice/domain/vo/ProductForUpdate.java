package com.ed.productservice.domain.vo;


public record ProductForUpdate(
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

}
