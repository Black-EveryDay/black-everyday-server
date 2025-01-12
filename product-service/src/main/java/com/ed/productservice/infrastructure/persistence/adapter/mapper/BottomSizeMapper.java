package com.ed.productservice.infrastructure.persistence.adapter.mapper;

import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.infrastructure.persistence.entity.size.BottomSizeStockEntity;
import org.springframework.stereotype.Component;

@Component
public class BottomSizeMapper {

  public BottomSizeStockEntity from(Long productId, BottomProduct.BottomSize bottomSize) {
    return new BottomSizeStockEntity(
        productId,
        bottomSize.getBottomSize(),
        bottomSize.getBottomTotalLength(),
        bottomSize.getThighWidth(),
        bottomSize.getHipWidth(),
        bottomSize.getQuantity()
    );
  }
}