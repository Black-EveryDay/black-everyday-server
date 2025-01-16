package com.ed.productservice.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "ED_PRODUCT_PRICE_VERSION")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPriceVersionEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PRODUCT_PRICE_VERSION_ID")
  private Long productPriceVersionId;

  @Column(name = "PRODUCT_ID")
  private Long productId;

  @Column(name = "PRICE")
  private Integer price;

  @Column(name = "VERSION")
  private int version;

  @Column(name = "IS_CURRENT_VERSION", nullable = false)
  private boolean isCurrentVersion;

  public static ProductPriceVersionEntity of(Long productId, int price, int version) {
    return ProductPriceVersionEntity.builder()
        .productId(productId)
        .price(price)
        .version(version + 1)
        .isCurrentVersion(true)
        .build();
  }

  public static ProductPriceVersionEntity of(Long productId, int price) {
    return ProductPriceVersionEntity.builder()
        .productId(productId)
        .price(price)
        .version(1)
        .isCurrentVersion(true)
        .build();
  }

  public void archivePreviousVersion() {
    this.isCurrentVersion = false;
  }
}
