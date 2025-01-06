package com.ed.productservice.application.port.out;

import com.ed.productservice.domain.ProductForCreate;
import com.ed.productservice.domain.vo.Product;

public interface ProductOutPort {

    Product createProduct(ProductForCreate productForCreate, Long brandId);
}
