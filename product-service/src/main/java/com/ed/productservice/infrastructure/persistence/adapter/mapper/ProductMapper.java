package com.ed.productservice.infrastructure.persistence.adapter.mapper;

import com.ed.productservice.domain.ProductForCreate;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.infrastructure.persistence.entity.ProductEntity;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  public ProductEntity from(ProductForCreate productForCreate, Long brandId) {
    return ProductEntity.builder().
        productPublicId(createPublicId())
        .brandId(brandId)
        .name(productForCreate.getName())
        .description(productForCreate.getDescription())
        .color(productForCreate.getColor())
        .image(productForCreate.getImage())
        .status(productForCreate.getStatus())
        .category(productForCreate.getCategory())
        .build();
  }

  private static String createPublicId() {
    return UUID.randomUUID().toString();
  }

  public Product toDomain(ProductEntity entity, int price) {
    return Product.builder()
        .productId(entity.getProductId())
        .productPublicId(entity.getProductPublicId())
        .brandId(entity.getBrandId())
        .name(entity.getName())
        .price(price)
        .description(entity.getDescription())
        .color(entity.getColor())
        .image(entity.getImage())
        .status(entity.getStatus())
        .category(entity.getCategory())
        .createdAt(entity.getCreatedAt()).build();
  }
}
