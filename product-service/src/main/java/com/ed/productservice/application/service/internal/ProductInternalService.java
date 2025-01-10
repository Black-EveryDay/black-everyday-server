package com.ed.productservice.application.service.internal;

import com.ed.productservice.application.port.out.BrandOutPort;
import com.ed.productservice.application.port.out.ProductOutPort;
import com.ed.productservice.domain.DecreaseStockResponse;
import com.ed.productservice.domain.DecreaseStockResponse.ProductBrandInfo;
import com.ed.productservice.domain.vo.Brand;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ProductInternalService {

  private final StockService stockService;
  private final BrandOutPort brandOutPort;
  private final ProductOutPort productOutPort;

  @Transactional
  public DecreaseStockResponse decreaseStock(List<ProductReservationInfoDomain> request) {
    String transactionId = UUID.randomUUID().toString();
    List<ProductBrandInfo> productBrandInfoList = new ArrayList<>();

    for (ProductReservationInfoDomain item : request) {
      Product product = productOutPort.findOne(item.getProductPublicId());
      stockService.decreaseStockReservation(item, transactionId, product.getProductId());

      getBrandIdList(item, productBrandInfoList);
    }

    return new DecreaseStockResponse(transactionId, productBrandInfoList);
  }

  private void getBrandIdList(ProductReservationInfoDomain item,
      List<ProductBrandInfo> productBrandInfoList) {
    Product product = productOutPort.findOne(item.getProductPublicId());
    Brand brand = brandOutPort.findOne(product.getBrandId());

    productBrandInfoList.add(new ProductBrandInfo(brand.getBrandId(), product.getProductId()));
  }

  @Transactional
  public String increaseStock(String transactionId) {
    stockService.increaseStock(transactionId);

    return transactionId;
  }
}