package com.ed.productservice.infrastructure.persistence.entity;

import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "ed_product")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;
  private String productPublicId;
  private Long brandId;
  private String name;
  private int price;
  private String description;
  private String color;
  private String image;

  @Enumerated(EnumType.STRING)
  private ProductStatus status;
  @Enumerated(EnumType.STRING)
  private ProductCategory category;

  public ProductEntity(String productPublicId, Long brandId, String name, int price,
      String description, String color, String image, ProductStatus status,
      ProductCategory category) {
    this.productPublicId = productPublicId;
    this.brandId = brandId;
    this.name = name;
    this.price = price;
    this.description = description;
    this.color = color;
    this.image = image;
    this.status = status;
    this.category = category;
  }

  public void update(Product product) {
    this.brandId = product.getProductId();
    this.name = product.getName();
    this.price = product.getPrice();
    this.description = product.getDescription();
    this.color = product.getColor();
    this.image = product.getImage();
    this.status = product.getStatus();
    this.category = product.getCategory();
  }
}
