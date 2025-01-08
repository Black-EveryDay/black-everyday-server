package com.ed.productservice.infrastructure.persistence.search;

import com.ed.productservice.domain.vo.ProductCategory;


public record ProductSearchCondition(
    String productName,
    String color,
    ProductCategory category,
    Integer minPrice,
    Integer maxPrice,
    String brandName
) { }
