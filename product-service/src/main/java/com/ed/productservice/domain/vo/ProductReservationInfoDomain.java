package com.ed.productservice.domain.vo;

import com.ed.productservice.infrastructure.persistence.entity.StockDecreaseHistoryEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductReservationInfoDomain {
    private Long productId;
    private int quantity;
    private String size;
    private ProductCategory productCategory;

    public ProductReservationInfoDomain(Long productId, int quantity, String size,
        ProductCategory productCategory) {
        this.productId = productId;
        this.quantity = quantity;
        this.size = size;
        this.productCategory = productCategory;
    }

    public static ProductReservationInfoDomain from(StockDecreaseHistoryEntity entity) {
        return new ProductReservationInfoDomain(
            entity.getProductId(),
            entity.getQuantity(),
            entity.getSize(),
            entity.getProductCategory(
            ));
    }
}
