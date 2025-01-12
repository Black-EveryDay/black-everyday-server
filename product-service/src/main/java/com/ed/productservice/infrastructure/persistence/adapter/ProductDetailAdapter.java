package com.ed.productservice.infrastructure.persistence.adapter;

import com.ed.productservice.application.port.out.ProductDetailPort;
import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.domain.TopProduct;
import com.ed.productservice.infrastructure.persistence.adapter.mapper.BottomSizeMapper;
import com.ed.productservice.infrastructure.persistence.adapter.mapper.TopSizeMapper;
import com.ed.productservice.infrastructure.persistence.repository.BottomSizeStockRepository;
import com.ed.productservice.infrastructure.persistence.repository.TopSizeStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDetailAdapter implements ProductDetailPort {

  private final TopSizeStockRepository topSizeStockRepository;
  private final BottomSizeStockRepository bottomSizeStockRepository;
  private final TopSizeMapper topSizeMapper;
  private final BottomSizeMapper bottomSizeMapper;

  @Override
  public void saveTopSize(Long productId, TopProduct topProduct) {
    topProduct.getTopSizeList()
        .forEach(topSize -> topSizeStockRepository.save(topSizeMapper.from(productId, topSize)));
  }

  @Override
  public void saveBottomSize(Long productId, BottomProduct bottomProduct) {
    bottomProduct.getBottomSizeList()
        .forEach(bottomSize -> bottomSizeStockRepository.save(
            bottomSizeMapper.from(productId, bottomSize)));
  }
}

