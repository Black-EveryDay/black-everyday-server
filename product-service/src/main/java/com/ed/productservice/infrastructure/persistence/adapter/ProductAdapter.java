package com.ed.productservice.infrastructure.persistence.adapter;

import static com.ed.productservice.libs.common.ErrorCode.PRODUCT_NOT_FOUND;
import static com.ed.productservice.libs.common.ErrorCode.PRODUCT_PRICE_NOT_FOUND;

import com.ed.productservice.application.port.out.ProductOutPort;
import com.ed.productservice.domain.ProductForCreate;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.domain.vo.ProductForUpdate;
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

    ProductPriceVersionEntity productPriceVersionEntity = getCurrentProductPriceVersionEntity(entity);

    return productMapper.toDomain(entity, productPriceVersionEntity.getPrice());
  }

  @Override
  public Product update(ProductForUpdate productForUpdate) {
    ProductEntity entity = updateProductEntity(productForUpdate);

    ProductPriceVersionEntity productPriceVersionEntity = updateProductPrice(productForUpdate,
        entity);

    return productMapper.toDomain(entity, productPriceVersionEntity.getPrice());
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

  private void createProductPriceVersion(ProductForCreate productForCreate,
      ProductEntity productEntity) {
    ProductPriceVersionEntity entity = ProductPriceVersionEntity.of(productEntity.getProductId(),
        productForCreate.getPrice());

    productPriceVersionRepository.save(entity);
  }

  private ProductPriceVersionEntity updateProductPrice(ProductForUpdate productForUpdate,
      ProductEntity entity) {
    var currentProductPriceVersionEntity = getCurrentProductPriceVersionEntity(entity);

    if (isNotChangedPrice(productForUpdate, currentProductPriceVersionEntity)) {
      return currentProductPriceVersionEntity;
    }

    return createNewPriceVersion(productForUpdate, entity, currentProductPriceVersionEntity);
  }

  private ProductPriceVersionEntity getCurrentProductPriceVersionEntity(ProductEntity entity) {

    return productPriceVersionRepository.findByProductId(entity.getProductId())
        .orElseThrow(() -> new ProductException(PRODUCT_PRICE_NOT_FOUND));
  }

  private static boolean isNotChangedPrice(ProductForUpdate productForUpdate,
      ProductPriceVersionEntity currentProductPriceVersionEntity) {

    return productForUpdate.price() == currentProductPriceVersionEntity.getPrice();
  }

  private ProductPriceVersionEntity createNewPriceVersion(ProductForUpdate productForUpdate,
      ProductEntity entity, ProductPriceVersionEntity currentProductPriceVersionEntity) {
    var newPrice = ProductPriceVersionEntity.of(entity.getProductId(), productForUpdate.price(),
        currentProductPriceVersionEntity.getVersion());

    currentProductPriceVersionEntity.deletedFrom();

    return productPriceVersionRepository.save(newPrice);
  }

  private ProductEntity updateProductEntity(ProductForUpdate productForUpdate) {
    var productEntity = productRepository.findByProductPublicId(
            productForUpdate.productPublicId())
        .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));

    productEntity.update(Product.from(productForUpdate));

    return productEntity;
  }
}
