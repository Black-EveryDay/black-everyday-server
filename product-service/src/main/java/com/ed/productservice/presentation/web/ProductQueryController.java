package com.ed.productservice.presentation.web;

import com.ed.productservice.domain.vo.ProductInfoDto;
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

    @GetMapping("/{productId}")
    public ProductDetailResponse findOne(@PathVariable("productId") Long productId) {
        ProductInfoDto dto = productQueryAdapter.findById(productId);

        return ProductDetailResponse.from(dto);

    }

    @GetMapping()
    public Page<ProductDetailResponse> getShopList(ProductSearchCondition condition,
        Pageable pageable) {

        return productQueryAdapter.search(condition, pageable)
            .map(ProductDetailResponse::from);
    }
}
