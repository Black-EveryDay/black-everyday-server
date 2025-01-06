package com.ed.productservice.presentation.web;

import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.domain.TopProduct;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.presentation.port.in.ProductCreateUseCase;
import com.ed.productservice.presentation.web.request.BottomProductCreateRequest;
import com.ed.productservice.presentation.web.request.TopProductCreateRequest;
import com.ed.productservice.presentation.web.response.ProductCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@RestController
public class ProductCommandController {
    private final ProductCreateUseCase productCreateUseCase;

    @PostMapping("/top")
    public ProductCreateResponse createApparelTop(@RequestBody TopProductCreateRequest request) {
        TopProduct domain = request.toDomain();

        Product product = productCreateUseCase.createApparelTop(domain);

        return ProductCreateResponse.from(product);
    }

    @PostMapping("/bottom")
    public ProductCreateResponse createApparelBottom(@RequestBody BottomProductCreateRequest request) {
        BottomProduct bottomProduct = request.toDomain();

        Product product = productCreateUseCase.createApparelBottom(bottomProduct);

        return ProductCreateResponse.from(product);
    }
}
