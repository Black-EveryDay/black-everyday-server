package com.ed.productservice.infrastructure.persistence.entity;

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
  private Long productPriceVersion;

  private Long productId;

  private Integer price;
  private Long version;

  public static ProductPriceVersionEntity of(Long productId, int price) {
    return ProductPriceVersionEntity.builder()
        .productId(productId)
        .price(price)
        .version(1L)
        .build();
  }
}
