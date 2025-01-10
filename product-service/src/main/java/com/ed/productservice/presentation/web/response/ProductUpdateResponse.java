package com.ed.productservice.presentation.web.response;

import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.domain.vo.ProductCategory;


public record ProductUpdateResponse(
    Long productId,
    String color,
    String image,
    ProductCategory category,
    String description,
    String name,
    Integer price
) {

  public static ProductUpdateResponse from(Product product) {
    return new ProductUpdateResponse(
        product.getProductId(),
        product.getColor(),
        product.getImage(),
        product.getCategory(),
        product.getDescription(),
        product.getName(),
        product.getPrice()
    );
  }
}
