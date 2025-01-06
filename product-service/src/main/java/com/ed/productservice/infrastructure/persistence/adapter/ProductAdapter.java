package com.ed.productservice.infrastructure.persistence.adapter;

import com.ed.productservice.application.port.out.ProductOutPort;
import com.ed.productservice.domain.ProductForCreate;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.infrastructure.persistence.adapter.mapper.ProductMapper;
import com.ed.productservice.infrastructure.persistence.entity.ProductEntity;
import com.ed.productservice.infrastructure.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductAdapter implements ProductOutPort {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Product createProduct(ProductForCreate productForCreate, Long brandId) {
        ProductEntity entity = productMapper.from(productForCreate, brandId);

        return productMapper.toDomain(productRepository.save(entity));
    }
}
