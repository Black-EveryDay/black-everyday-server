package com.ed.productservice.domain.vo;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
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
    return Product.builder()
        .brandId(productForUpdate.brandId())
        .name(productForUpdate.name())
        .price(productForUpdate.price())
        .description(productForUpdate.description())
        .color(productForUpdate.color())
        .image(productForUpdate.image())
        .status(productForUpdate.status())
        .category(productForUpdate.category())
        .build();
  }
}
