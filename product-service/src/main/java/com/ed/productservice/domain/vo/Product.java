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

    public Product update(ProductForUpdate request) {
        this.brandId = request.brandId();
        this.name = request.name();
        this.price = request.price();
        this.description = request.description();
        this.color = request.color();
        this.image = request.image();
        this.status = request.status();
        this.category = request.category();

        return this;
    }
}
