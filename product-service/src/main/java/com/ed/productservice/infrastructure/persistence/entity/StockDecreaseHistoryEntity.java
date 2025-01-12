package com.ed.productservice.infrastructure.persistence.entity;

import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.StockDecreaseHistoryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "ED_STOCK_DECREASE_HISTORY")
@Entity
@NoArgsConstructor
public class StockDecreaseHistoryEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  private Long id;

  @Column(name = "PRODUCT_PUBLIC_ID", nullable = false)
  private String productPublicId;

  @Column(name = "QUANTITY", nullable = false)
  private Integer quantity;

  @Column(name = "SIZE", nullable = false)
  private String size;

  @Column(name = "PRODUCT_CATEGORY", nullable = false)
  @Enumerated(EnumType.STRING)
  private ProductCategory productCategory;

  @Column(name = "TRANSACTION_ID", nullable = false)
  private String transactionId;

  @Column(name = "STATUS", nullable = false)
  @Enumerated(EnumType.STRING)
  private StockDecreaseHistoryStatus status;

  public StockDecreaseHistoryEntity(String productPublicId, Integer quantity, String size,
      ProductCategory productCategory, String transactionId, StockDecreaseHistoryStatus status) {
    this.productPublicId = productPublicId;
    this.quantity = quantity;
    this.size = size;
    this.productCategory = productCategory;
    this.transactionId = transactionId;
    this.status = status;
  }

  public void setStatus(StockDecreaseHistoryStatus status) {
    this.status = status;
  }
}