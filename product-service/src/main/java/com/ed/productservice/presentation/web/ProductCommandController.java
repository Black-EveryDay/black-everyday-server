package com.ed.productservice.presentation.web;

import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.domain.TopProduct;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.domain.vo.ProductForUpdate;
import com.ed.productservice.presentation.port.in.ProductUseCase;
import com.ed.productservice.presentation.web.request.BottomProductCreateRequest;
import com.ed.productservice.presentation.web.request.TopProductCreateRequest;
import com.ed.productservice.presentation.web.request.UpdateProductRequest;
import com.ed.productservice.presentation.web.response.ProductCreateResponse;
import com.ed.productservice.presentation.web.response.ProductUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@RestController
public class ProductCommandController {
    private final ProductUseCase productUseCase;

    @PostMapping("/top")
    public ProductCreateResponse createApparelTop(@RequestBody TopProductCreateRequest request) {
        TopProduct domain = request.toDomain();

        Product product = productUseCase.createApparelTop(domain);

        return ProductCreateResponse.from(product);
    }

    @PostMapping("/bottom")
    public ProductCreateResponse createApparelBottom(@RequestBody BottomProductCreateRequest request) {
        BottomProduct bottomProduct = request.toDomain();

        Product product = productUseCase.createApparelBottom(bottomProduct);

        return ProductCreateResponse.from(product);
    }

    @PatchMapping
    public ProductUpdateResponse updateProduct(@RequestBody UpdateProductRequest request) {
        ProductForUpdate productForUpdate = request.toDomain();

        Product product = productUseCase.updateProduct(productForUpdate);

        return ProductUpdateResponse.from(product);
    }

    @DeleteMapping("/{productPublicId}")
    public void updateProduct(@PathVariable("productPublicId") String productPublicId) {
        productUseCase.deleteProduct(productPublicId);
    }
}
