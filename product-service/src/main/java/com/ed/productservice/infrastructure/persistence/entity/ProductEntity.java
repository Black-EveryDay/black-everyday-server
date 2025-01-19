package com.ed.productservice.infrastructure.persistence.entity;

import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductStatus;
import jakarta.persistence.Column;
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
@Table(name = "ED_PRODUCT")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PRODUCT_ID", nullable = false)
  private Long productId;

  @Column(name = "PRODUCT_PUBLIC_ID", nullable = false)
  private String productPublicId;

  @Column(name = "BRAND_ID", nullable = false)
  private Long brandId;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "DESCRIPTION", nullable = false)
  private String description;

  @Column(name = "COLOR", nullable = false)
  private String color;

  @Column(name = "IMAGE", nullable = false)
  private String image;

  @Column(name = "STATUS", nullable = false)
  @Enumerated(EnumType.STRING)
  private ProductStatus status;

  @Column(name = "CATEGORY", nullable = false)
  @Enumerated(EnumType.STRING)
  private ProductCategory category;

  public ProductEntity(String productPublicId, Long brandId, String name,
      String description, String color, String image, ProductStatus status,
      ProductCategory category) {
    this.productPublicId = productPublicId;
    this.brandId = brandId;
    this.name = name;
    this.description = description;
    this.color = color;
    this.image = image;
    this.status = status;
    this.category = category;
  }

  public void update(Product product) {
    this.brandId = product.getBrandId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.color = product.getColor();
    this.image = product.getImage();
    this.status = product.getStatus();
    this.category = product.getCategory();
  }
}