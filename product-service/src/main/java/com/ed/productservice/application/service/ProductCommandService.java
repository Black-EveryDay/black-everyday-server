package com.ed.productservice.application.service;

import com.ed.productservice.application.port.out.BrandOutPort;
import com.ed.productservice.application.port.out.ProductDetailPort;
import com.ed.productservice.application.port.out.ProductOutPort;
import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.domain.TopProduct;
import com.ed.productservice.domain.vo.Brand;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.presentation.port.in.ProductCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCommandService implements ProductCreateUseCase {
    private final ProductOutPort productOutPort;
    private final BrandOutPort brandOutPort;
    private final ProductDetailPort productDetailPort;

    public Product createApparelTop(TopProduct topProduct) {
        Brand brand = brandOutPort.findOne(topProduct.getProductForCreate().getBrandId());

        Product product = productOutPort.createProduct(topProduct.getProductForCreate(), brand.getBrandId());

        productDetailPort.saveTopSize(product.getProductId(), topProduct);

        return product;
    }

    @Override
    public Product createApparelBottom(BottomProduct bottomProduct) {
        Brand brand = brandOutPort.findOne(bottomProduct.getProductForCreate().getBrandId());

        Product product = productOutPort.createProduct(bottomProduct.getProductForCreate(), brand.getBrandId());

        productDetailPort.saveBottomSize(product.getProductId(), bottomProduct);

        return product;
    }
}