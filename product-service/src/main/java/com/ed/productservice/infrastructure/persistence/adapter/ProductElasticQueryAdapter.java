package com.ed.productservice.infrastructure.persistence.adapter;

import com.ed.productservice.infrastructure.persistence.elasticsearch.SearchProduct;
import com.ed.productservice.infrastructure.persistence.repository.elasticsearch.SearchProductRepository;
import com.ed.productservice.infrastructure.persistence.repository.elasticsearch.AdvancedSearchProductRepository;
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
public class ProductElasticQueryAdapter {

  private final SearchProductRepository searchProductRepository;
  private final AdvancedSearchProductRepository advancedSearchProductRepository;

  public SearchProduct findOne(String productPublicId) {

    return searchProductRepository.findByProductPublicId(productPublicId)
        .orElseThrow(() -> new ProductException(
            ErrorCode.PRODUCT_NOT_FOUND));
  }

  public Page<SearchProduct> search(ProductSearchCondition condition, Pageable pageable) {

    return advancedSearchProductRepository.search(
        condition.productName(),
        condition.color(),
        condition.category() != null? condition.category().name() : null,
        condition.minPrice(),
        condition.maxPrice(),
        condition.brandName(),
        pageable
    );
  }
}