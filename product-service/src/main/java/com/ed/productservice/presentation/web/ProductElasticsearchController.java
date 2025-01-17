package com.ed.productservice.presentation.web;

import com.ed.productservice.infrastructure.persistence.adapter.ProductElasticQueryAdapter;
import com.ed.productservice.infrastructure.persistence.elasticsearch.SearchProduct;
import com.ed.productservice.infrastructure.persistence.search.ProductSearchCondition;
import com.ed.productservice.presentation.web.response.ProductDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/product")
@RequiredArgsConstructor
public class ProductElasticsearchController {

  private final ProductElasticQueryAdapter productElasticQueryAdapter;

  @GetMapping("/{productPublicId}")
  public SearchProduct getProducts(@PathVariable("productPublicId") String productPublicId) {
    return productElasticQueryAdapter.findOne(productPublicId);
  }

  @GetMapping
  public Page<ProductDetailResponse> getProductList(ProductSearchCondition condition,
      Pageable pageable) {

    return productElasticQueryAdapter.search(condition, pageable)
        .map(ProductDetailResponse::from);
  }
}
