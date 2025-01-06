package com.ed.productservice.domain;

import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductStatus;
import lombok.Getter;

@Getter
public class ProductForCreate {
    private Long brandId;
    private String name;
    private int price;
    private String description;
    private ProductStatus status;
    private ProductCategory category;
    private String color;
    private Integer quantity;
    private String image;

    public ProductForCreate(Long brandId, String name, int price, String description, ProductStatus status, ProductCategory category, String color, Integer quantity, String image) {
        this.brandId = brandId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.status = status;
        this.category = category;
        this.color = color;
        this.quantity = quantity;
        this.image = image;
        validateProduct();
    }

    private void validateProduct() {
        if (!name.matches("^[가-힣a-zA-Z0-9\\s]+$")) {
            throw new IllegalArgumentException("상품명은 한글, 영문, 숫자만 가능합니다.");  // 메시지도 함께 수정
        }

        if (price < 0) {
            throw new IllegalArgumentException("가격은 음수가 될 수 없습니다.");
        }

        if (quantity < 0) {
            throw new IllegalArgumentException("수량은 음수가 될 수 없습니다.");
        }
    }
}
