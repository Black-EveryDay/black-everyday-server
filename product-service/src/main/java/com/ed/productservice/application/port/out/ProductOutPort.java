package com.ed.productservice.application.port.out;

import com.ed.productservice.domain.ProductForCreate;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.domain.vo.ProductForUpdate;

public interface ProductOutPort {

  Product createProduct(ProductForCreate productForCreate, Long brandId);

  Product findOne(String productPublicId);

  Product update(ProductForUpdate product);

  void deleteOne(String productPublicId);
}
