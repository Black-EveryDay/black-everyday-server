package com.ed.orderservice.infrastructure.external.fegin.domain.product.dto;

public enum ProductCategory {
  TOP("상의"),
  BOTTOM("하의");

  private final String description;

  ProductCategory(String description) {

    this.description = description;
  }

  public String getDescription() {

    return description;
  }
}
