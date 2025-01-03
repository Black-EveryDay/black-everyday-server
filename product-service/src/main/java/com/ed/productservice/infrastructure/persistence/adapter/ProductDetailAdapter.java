package com.ed.productservice.infrastructure.persistence.adapter;

import com.ed.productservice.application.port.out.ProductDetailPort;
import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.domain.TopProduct;
import com.ed.productservice.infrastructure.persistence.entity.BottomSizeEntity;
import com.ed.productservice.infrastructure.persistence.entity.TopSizeEntity;
import com.ed.productservice.infrastructure.persistence.repository.BottomSizeRepository;
import com.ed.productservice.infrastructure.persistence.repository.TopSizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDetailAdapter implements ProductDetailPort {
    private final TopSizeRepository topSizeRepository;
    private final BottomSizeRepository bottomSizeRepository;

    @Override
    public void saveTopSize(Long productId, TopProduct topProduct) {
        topProduct.getTopSizeList()
                .forEach(topSize -> topSizeRepository.save(TopSizeEntity.from(productId, topSize)));
    }

    @Override
    public void saveBottomSize(Long productId, BottomProduct bottomProduct) {
        bottomProduct.getBottomSizeList()
                .forEach(bottomSize -> bottomSizeRepository.save(BottomSizeEntity.from(productId, bottomSize)));
    }
}

