package com.ed.productservice.presentation.web.response;

import com.ed.productservice.domain.vo.ProductDetails;
import com.ed.productservice.infrastructure.persistence.elasticsearch.SearchProduct;


public record ProductDetailResponse(
    String productPublicId,
    String color,
    String image,
    String category,
    String description,
    String name,
    Integer price,
    String brandName
) {

  public static ProductDetailResponse from(ProductDetails dto) {
    return new ProductDetailResponse(
        dto.productPublicId(),
        dto.color(),
        dto.image(),
        dto.category().name(),
        dto.description(),
        dto.name(),
        dto.price(),
        dto.brandName()
    );
  }

  public static ProductDetailResponse from(SearchProduct searchProduct) {
    return new ProductDetailResponse(
        searchProduct.getProductPublicId(),
        searchProduct.getColor(),
        searchProduct.getImage(),
        searchProduct.getCategory(),
        searchProduct.getDescription(),
        searchProduct.getName(),
        searchProduct.getPrice(),
        searchProduct.getBrandName()
    );
  }
}
