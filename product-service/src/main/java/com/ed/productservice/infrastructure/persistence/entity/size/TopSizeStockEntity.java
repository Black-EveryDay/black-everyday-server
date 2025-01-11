package com.ed.productservice.infrastructure.persistence.entity.size;

import static com.ed.productservice.libs.common.ErrorCode.INSUFFICIENT_PRODUCT_STOCK;

import com.ed.productservice.infrastructure.persistence.entity.BaseEntity;
import com.ed.productservice.libs.common.ProductException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ED_TOP_SIZE_STOCK")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopSizeStockEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TOP_SIZE_ID", nullable = false)
  private Long topSizeId;

  @Column(name = "PRODUCT_ID", nullable = false)
  private Long productId;

  @Column(name = "TOP_SIZE", nullable = false)
  private String topSize;

  @Column(name = "TOTAL_LENGTH", nullable = false)
  private BigDecimal totalLength;

  @Column(name = "SHOULDER_WIDTH", nullable = false)
  private BigDecimal shoulderWidth;

  @Column(name = "CHEST_WIDTH", nullable = false)
  private BigDecimal chestWidth;

  @Column(name = "SLEEVE_LENGTH", nullable = false)
  private BigDecimal sleeveLength;

  @Column(name = "QUANTITY", nullable = false)
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