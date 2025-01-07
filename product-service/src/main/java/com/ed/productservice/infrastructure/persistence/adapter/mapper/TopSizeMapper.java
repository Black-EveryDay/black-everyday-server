package com.ed.productservice.infrastructure.persistence.adapter.mapper;

import com.ed.productservice.domain.TopProduct;
import com.ed.productservice.infrastructure.persistence.entity.size.TopSizeStockEntity;
import org.springframework.stereotype.Component;

@Component
public class TopSizeMapper {
    public TopSizeStockEntity from(Long productId, TopProduct.TopSize topSize) {
        return new TopSizeStockEntity(
                productId,
                topSize.getTopSize(),
                topSize.getTotalLength(),
                topSize.getShoulderWidth(),
                topSize.getChestWidth(),
                topSize.getSleeveLength(),
                topSize.getQuantity()
        );
    }
}
