package com.ed.productservice.infrastructure.persistence.adapter;

import static com.ed.productservice.libs.common.ErrorCode.PRODUCT_NOT_FOUND;

import com.ed.productservice.application.port.out.ProductOutPort;
import com.ed.productservice.domain.ProductForCreate;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.infrastructure.persistence.adapter.mapper.ProductMapper;
import com.ed.productservice.infrastructure.persistence.entity.ProductEntity;
import com.ed.productservice.infrastructure.persistence.repository.ProductRepository;
import com.ed.productservice.libs.common.ProductException;
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

    @Override
    public Product findOne(Long productId) {
        ProductEntity entity = productRepository.findById(productId)
            .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));

        return productMapper.toDomain(entity);
    }

    @Override
    public Product update(Product product) {
        ProductEntity entity = productRepository.findById(product.getProductId())
            .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));

        entity.update(product);

        return productMapper.toDomain(entity);
    }

    @Override
    public void deleteOne(String productPublicId) {
        ProductEntity entity = productRepository.findByProductPublicId(productPublicId)
            .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));

        entity.deletedFrom();
    }
}
