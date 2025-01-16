package com.ed.productservice.infrastructure.persistence.adapter;

import com.ed.productservice.infrastructure.persistence.elasticsearch.SearchProduct;
import com.ed.productservice.infrastructure.persistence.repository.SearchProductRepository;
import com.ed.productservice.libs.common.ErrorCode;
import com.ed.productservice.libs.common.ProductException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductElasticQueryAdapter {

  private final SearchProductRepository searchProductRepository;

  public SearchProduct findOne(String productPublicId) {

    return searchProductRepository.findByProductPublicId(productPublicId)
        .orElseThrow(() -> new ProductException(
            ErrorCode.PRODUCT_NOT_FOUND));
  }
}