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
@Table(name = "ed_bottom_size_stock")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BottomSizeStockEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bottom_size_id")
  private Long bottomSizeId;

  @Column(name = "product_id", nullable = false)
  private Long productId;

  @Column(name = "bottom_size", nullable = false)
  private String bottomSize;

  @Column(name = "total_length", nullable = false, precision = 5, scale = 1)
  private BigDecimal totalLength;

  @Column(name = "thigh_circumference", nullable = false, precision = 5, scale = 1)
  private BigDecimal thighCircumference;

  @Column(name = "hip_width", nullable = false, precision = 5, scale = 1)
  private BigDecimal hipWidth;

  @Column(name = "quantity", nullable = false)
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