package com.ed.productservice.infrastructure.persistence.adapter.mapper;

import com.ed.productservice.domain.TopProduct;
import com.ed.productservice.infrastructure.persistence.entity.size.TopSizeEntity;
import org.springframework.stereotype.Component;

@Component
public class TopSizeMapper {
    public TopSizeEntity from(Long productId, TopProduct.TopSize topSize) {
        return new TopSizeEntity(
                productId,
                topSize.getTopSize(),
                topSize.getTotalLength(),
                topSize.getShoulderWidth(),
                topSize.getChestWidth(),
                topSize.getSleeveLength()
        );
    }
}
