package com.ed.productservice.infrastructure.persistence.adapter;

import com.ed.productservice.domain.vo.ProductInfoDto;
import com.ed.productservice.infrastructure.persistence.repository.ProductQueryDslRepository;
import com.ed.productservice.infrastructure.persistence.repository.ProductRepository;
import com.ed.productservice.infrastructure.persistence.search.ProductSearchCondition;
import com.ed.productservice.libs.common.ErrorCode;
import com.ed.productservice.libs.common.ProductException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductQueryAdapter {

  private final ProductRepository productRepository;
  private final ProductQueryDslRepository productQueryDslRepository;

  public ProductInfoDto findById(String productPublicId) {

    return productRepository.findByProductAndCurrentPrice(productPublicId)
        .orElseThrow(() -> new ProductException(
            ErrorCode.PRODUCT_NOT_FOUND));
  }

  public Page<ProductInfoDto> search(ProductSearchCondition condition, Pageable pageable) {

    return productQueryDslRepository.search(condition.productName(),
        condition.color(),
        condition.category(),
        condition.minPrice(),
        condition.maxPrice(),
        condition.brandName(),
        pageable);
  }

  public ProductInfoDto getProductByVersion(String productPublicId, int version) {

    return productRepository.findByProductAndPriceVersion(productPublicId, version)
        .orElseThrow(() -> new ProductException(
            ErrorCode.PRODUCT_NOT_FOUND));
  }
}