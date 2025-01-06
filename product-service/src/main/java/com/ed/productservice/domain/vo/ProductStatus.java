package com.ed.productservice.domain.vo;

public enum ProductStatus {
    ACTIVE("판매중"),
    INACTIVE("판매중지"),
    SOLD_OUT("품절");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
