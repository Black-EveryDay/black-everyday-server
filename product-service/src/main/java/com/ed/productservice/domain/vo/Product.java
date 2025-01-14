package com.ed.productservice.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

  private Long productId;
  private String productPublicId;
  private Long brandId;
  private String name;
  private int price;
  private String description;
  private String color;
  private String image;
  private ProductStatus status;
  private ProductCategory category;
  private LocalDateTime createdAt;

  public static Product from(ProductForUpdate productForUpdate) {
    return new Product(
        productForUpdate.brandId(),
        productForUpdate.name(),
        productForUpdate.price(),
        productForUpdate.description(),
        productForUpdate.color(),
        productForUpdate.image(),
        productForUpdate.status(),
        productForUpdate.category()
    );
  }

  public Product(Long brandId, String name, int price, String description, String color,
      String image,
      ProductStatus status, ProductCategory category) {
    this.brandId = brandId;
    this.name = name;
    this.price = price;
    this.description = description;
    this.color = color;
    this.image = image;
    this.status = status;
    this.category = category;
  }
}
