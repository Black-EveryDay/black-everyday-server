package com.ed.productservice.infrastructure.persistence.adapter;

import static com.ed.productservice.libs.common.ErrorCode.PRODUCT_NOT_FOUND;

import com.ed.productservice.application.port.out.ProductOutPort;
import com.ed.productservice.domain.ProductForCreate;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.infrastructure.persistence.adapter.mapper.ProductMapper;
import com.ed.productservice.infrastructure.persistence.entity.ProductEntity;
import com.ed.productservice.infrastructure.persistence.entity.ProductPriceVersionEntity;
import com.ed.productservice.infrastructure.persistence.repository.ProductPriceVersionRepository;
import com.ed.productservice.infrastructure.persistence.repository.ProductRepository;
import com.ed.productservice.libs.common.ProductException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductAdapter implements ProductOutPort {

  private final ProductRepository productRepository;
  private final ProductPriceVersionRepository productPriceVersionRepository;
  private final ProductMapper productMapper;

  @Override
  public Product createProduct(ProductForCreate productForCreate, Long brandId) {
    ProductEntity entity = createProductEntity(productForCreate, brandId);

    createProductPriceVersion(productForCreate, entity);

    return productMapper.toDomain(entity, productForCreate.getPrice());
  }

  @Override
  public Product findOne(String productPublicId) {
    ProductEntity entity = productRepository.findByProductPublicId(productPublicId)
        .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));

    ProductPriceVersionEntity productPriceVersionEntity = productPriceVersionRepository.findByProductId(
        entity.getProductId()).orElseThrow();

    return productMapper.toDomain(entity, productPriceVersionEntity.getPrice());
  }

  @Override
  public Product update(Product product) {
    ProductEntity entity = productRepository.findById(product.getProductId())
        .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));

    entity.update(product);
    return null;
//    return productMapper.toDomain(entity, productPriceVersionEntity.getPrice());
  }

  @Override
  public void deleteOne(String productPublicId) {
    ProductEntity entity = productRepository.findByProductPublicId(productPublicId)
        .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));

    entity.deletedFrom();
  }

  private ProductEntity createProductEntity(ProductForCreate productForCreate, Long brandId) {
    ProductEntity entity = productMapper.from(productForCreate, brandId);

    return productRepository.save(entity);
  }

  private void createProductPriceVersion(ProductForCreate productForCreate, ProductEntity productEntity) {
    ProductPriceVersionEntity entity = ProductPriceVersionEntity.of(productEntity.getProductId(),
        productForCreate.getPrice());

    productPriceVersionRepository.save(entity);
  }
}
