package com.ed.productservice.infrastructure.persistence.entity;

import com.ed.productservice.domain.vo.ProductCategory;
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

    private Long productId;
    private Integer quantity;
    private String size;

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    private String transactionId;

    public StockDecreaseHistoryEntity(Long productId, Integer quantity, String size,
        ProductCategory productCategory, String transactionId) {
        this.productId = productId;
        this.quantity = quantity;
        this.size = size;
        this.productCategory = productCategory;
        this.transactionId = transactionId;
    }
}
