package com.ed.productservice.presentation.port.in;

import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.domain.TopProduct;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.domain.vo.ProductForUpdate;

public interface ProductUseCase {

  Product createApparelTop(TopProduct productForCreate);

  Product createApparelBottom(BottomProduct bottomProduct);

  Product updateProduct(ProductForUpdate request);

  void deleteProduct(String productPublicId);
}
