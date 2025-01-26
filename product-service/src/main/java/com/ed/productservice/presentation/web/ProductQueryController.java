package com.ed.productservice.presentation.web;

import com.ed.productservice.domain.vo.ProductDetails;
import com.ed.productservice.infrastructure.persistence.adapter.ProductQueryAdapter;
import com.ed.productservice.infrastructure.persistence.search.ProductSearchCondition;
import com.ed.productservice.presentation.web.response.ProductDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductQueryController {

  private final ProductQueryAdapter productQueryAdapter;

  @GetMapping("/{productPublicId}")
  public ProductDetailResponse findOne(@PathVariable("productPublicId") String productPublicId) {
    ProductDetails dto = productQueryAdapter.findById(productPublicId);

    return ProductDetailResponse.from(dto);
  }

  @GetMapping("/{productPublicId}/{version}")
  public ProductDetailResponse findOneByVersion(@PathVariable("productPublicId") String productPublicId, @PathVariable ("version") int version) {
    ProductDetails dto = productQueryAdapter.getProductByVersion(productPublicId, version);

    return ProductDetailResponse.from(dto);
  }

  @GetMapping()
  public Page<ProductDetailResponse> getProductList(ProductSearchCondition condition,
      Pageable pageable) {

    return productQueryAdapter.search(condition, pageable)
        .map(ProductDetailResponse::from);
  }
}
