package com.ed.productservice.application.service;

import com.ed.productservice.application.port.out.BrandOutPort;
import com.ed.productservice.application.port.out.ProductDetailPort;
import com.ed.productservice.application.port.out.ProductOutPort;
import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.domain.TopProduct;
import com.ed.productservice.domain.vo.Brand;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.domain.vo.ProductForUpdate;
import com.ed.productservice.presentation.port.in.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCommandService implements ProductUseCase {

  private final ProductOutPort productOutPort;
  private final BrandOutPort brandOutPort;
  private final ProductDetailPort productDetailPort;

  @Override
  public Product createApparelTop(TopProduct topProduct) {
    Brand brand = getBrandForCreate(topProduct.getProductForCreate().getBrandId());

    Product product = productOutPort.createProduct(topProduct.getProductForCreate(),
        brand.getBrandId());

    productDetailPort.saveTopSize(product.getProductId(), topProduct);

    return product;
  }

  @Override
  public Product createApparelBottom(BottomProduct bottomProduct) {
    Brand brand = getBrandForCreate(bottomProduct.getProductForCreate().getBrandId());

    Product product = productOutPort.createProduct(bottomProduct.getProductForCreate(),
        brand.getBrandId());

    productDetailPort.saveBottomSize(product.getProductId(), bottomProduct);

    return product;
  }

  @Override
  public Product updateProduct(ProductForUpdate request) {
    brandOutPort.findOne(request.brandId());

    return productOutPort.update(request);
  }

  @Override
  public void deleteProduct(String productPublicId) {
    productOutPort.deleteOne(productPublicId);
  }

  private Brand getBrandForCreate(Long bottomProduct) {
    return brandOutPort.findOne(bottomProduct);
  }
}