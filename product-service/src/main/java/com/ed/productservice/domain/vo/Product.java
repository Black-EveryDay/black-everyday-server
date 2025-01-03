package com.ed.productservice.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long productId;
    private String productPublicId;
    private Long brandId;
    private String name;
    private int price;
    private String description;
    private String color;
    private Integer quantity;
    private String image;
    private ProductStatus status;
    private ProductCategory category;
    private LocalDateTime createdAt;
}
