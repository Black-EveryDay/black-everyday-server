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
@Table(name = "ED_BOTTOM_SIZE_STOCK")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BottomSizeStockEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "BOTTOM_SIZE_ID", nullable = false)
  private Long bottomSizeId;

  @Column(name = "PRODUCT_ID", nullable = false)
  private Long productId;

  @Column(name = "BOTTOM_SIZE", nullable = false)
  private String bottomSize;

  @Column(name = "TOTAL_LENGTH", nullable = false, precision = 5, scale = 1)
  private BigDecimal totalLength;

  @Column(name = "THIGH_CIRCUMFERENCE", nullable = false, precision = 5, scale = 1)
  private BigDecimal thighCircumference;

  @Column(name = "HIP_WIDTH", nullable = false, precision = 5, scale = 1)
  private BigDecimal hipWidth;

  @Column(name = "QUANTITY", nullable = false)
  private Integer quantity;

  public BottomSizeStockEntity(Long productId, String bottomSize, BigDecimal totalLength,
      BigDecimal thighCircumference, BigDecimal hipWidth, Integer quantity) {
    this.productId = productId;
    this.bottomSize = bottomSize;
    this.totalLength = totalLength;
    this.thighCircumference = thighCircumference;
    this.hipWidth = hipWidth;
    this.quantity = quantity;
  }

  public void validateStockAvailability(int requestQuantity) {
    if (this.quantity < requestQuantity) {
      throw new ProductException(INSUFFICIENT_PRODUCT_STOCK);
    }
  }

  public void decrease(int quantity) {
    this.quantity -= quantity;
  }

  public void increaseStock(int quantity) {
    this.quantity += quantity;
  }
}