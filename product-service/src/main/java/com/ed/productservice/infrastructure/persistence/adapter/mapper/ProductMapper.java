package com.ed.productservice.infrastructure.persistence.adapter.mapper;

import com.ed.productservice.domain.ProductForCreate;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.infrastructure.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductMapper {
    public ProductEntity from(ProductForCreate productForCreate, Long brandId) {
        return ProductEntity.builder().
                productPublicId(createPublicId())
                .brandId(brandId)
                .name(productForCreate.getName())
                .price(productForCreate.getPrice())
                .description(productForCreate.getDescription())
                .color(productForCreate.getColor())
                .quantity(productForCreate.getQuantity())
                .image(productForCreate.getImage())
                .status(productForCreate.getStatus())
                .category(productForCreate.getCategory())
                .build();
    }

    private static String createPublicId() {
        return UUID.randomUUID().toString();
    }

    public Product toDomain(ProductEntity entity) {
        return new Product(
                entity.getProductId(),
                entity.getProductPublicId(),
                entity.getBrandId(),
                entity.getName(),
                entity.getPrice(),
                entity.getDescription(),
                entity.getColor(),
                entity.getQuantity(),
                entity.getImage(),
                entity.getStatus(),
                entity.getCategory(),
                entity.getCreatedAt()
        );
    }
}
