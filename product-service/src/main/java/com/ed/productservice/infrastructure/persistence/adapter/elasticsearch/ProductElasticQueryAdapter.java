package com.ed.productservice.infrastructure.persistence.adapter.elasticsearch;

import com.ed.productservice.infrastructure.persistence.elasticsearch.SearchProduct;
import com.ed.productservice.infrastructure.persistence.repository.elasticsearch.AdvancedSearchProductRepository;
import com.ed.productservice.infrastructure.persistence.search.ProductSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductElasticQueryAdapter {

  private final AdvancedSearchProductRepository advancedSearchProductRepository;

  public Page<SearchProduct> search(ProductSearchCondition condition, Pageable pageable) {

    return advancedSearchProductRepository.search(
        condition.productName(),
        condition.color(),
        condition.category() != null ? condition.category().name() : null,
        condition.minPrice(),
        condition.maxPrice(),
        condition.brandName(),
        pageable
    );
  }
}