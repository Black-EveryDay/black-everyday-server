package com.ed.productservice.infrastructure.persistence.adapter.mapper;

import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.infrastructure.persistence.entity.size.BottomSizeEntity;
import org.springframework.stereotype.Component;

@Component
public class BottomSizeMapper {
    public BottomSizeEntity from(Long productId, BottomProduct.BottomSize bottomSize) {
        return new BottomSizeEntity(
                productId,
                bottomSize.getBottomSize(),
                bottomSize.getBottomTotalLength(),
                bottomSize.getThighWidth(),
                bottomSize.getHipWidth()
        );
    }
}