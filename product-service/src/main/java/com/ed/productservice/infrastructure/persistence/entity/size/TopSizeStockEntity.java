package com.ed.productservice.infrastructure.persistence.entity.size;

import static com.ed.productservice.libs.common.ErrorCode.INSUFFICIENT_PRODUCT_STOCK;

import com.ed.productservice.infrastructure.persistence.entity.BaseEntity;
import com.ed.productservice.libs.common.ProductException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "ed_top_size_stock")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopSizeStockEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "top_size_id")
  private Long topSizeId;

  @Column(name = "product_id", nullable = false)
  private Long productId;

  @Column(name = "top_size", nullable = false)
  private String topSize;

  @Column(name = "total_length", nullable = false)
  private BigDecimal totalLength;

  @Column(name = "shoulder_width", nullable = false)
  private BigDecimal shoulderWidth;

  @Column(name = "chest_width", nullable = false)
  private BigDecimal chestWidth;

  @Column(name = "sleeve_length", nullable = false)
  private BigDecimal sleeveLength;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  public TopSizeStockEntity(Long productId, String topSize, BigDecimal totalLength,
      BigDecimal shoulderWidth, BigDecimal chestWidth, BigDecimal sleeveLength,
      Integer quantity) {
    this.productId = productId;
    this.topSize = topSize;
    this.totalLength = totalLength;
    this.shoulderWidth = shoulderWidth;
    this.chestWidth = chestWidth;
    this.sleeveLength = sleeveLength;
    this.quantity = quantity;
  }

  public void validateStockAvailability(int requestQuantity) {
    if (this.quantity < requestQuantity) {
      throw new ProductException(INSUFFICIENT_PRODUCT_STOCK);
    }
  }

  public void decreaseStock(int quantity) {
    if (this.quantity < quantity) {
      throw new ProductException(INSUFFICIENT_PRODUCT_STOCK);
    }
    this.quantity -= quantity;
  }

  public void increaseStock(int quantity) {
    this.quantity += quantity;
  }
}