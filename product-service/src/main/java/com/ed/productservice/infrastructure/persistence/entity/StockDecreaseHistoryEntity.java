package com.ed.productservice.infrastructure.persistence.entity;

import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.StockDecreaseHistoryStatus;
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
@Table(name = "ed_stock_decrease_history")
@Entity
@NoArgsConstructor
public class StockDecreaseHistoryEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productPublicId;
    private Integer quantity;
    private String size;

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    private String transactionId;

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
