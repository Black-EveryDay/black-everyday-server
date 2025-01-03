package com.ed.productservice.domain.vo;

public enum BrandType {
    DESIGNER("디자이너"),
    CONTEMPORARY("컨템포러리"),
    STREET("스트릿"),
    MINIMAL("미니멀"),
    CASUAL("캐주얼"),
    VINTAGE("빈티지"),
    SPORTS("스포츠"),
    OUTDOOR("아웃도어");

    private final String description;

    BrandType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
