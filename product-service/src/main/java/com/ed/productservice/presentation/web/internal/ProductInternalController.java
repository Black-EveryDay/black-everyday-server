package com.ed.productservice.presentation.web.internal;

import static com.ed.productservice.presentation.web.response.ProductInternalResponse.StockIncreaseResponse;

import com.ed.productservice.application.service.internal.ProductInternalService;
import com.ed.productservice.domain.DecreaseStockResponse;
import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.presentation.web.request.StockPrepareRequest;
import com.ed.productservice.presentation.web.response.ProductInternalResponse.StockCommitResponse;
import com.ed.productservice.presentation.web.response.ProductInternalResponse.StockDecreaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/products/internal")
@RestController
public class ProductInternalController {

  private final ProductInternalService productInternalService;

  @PostMapping("/prepare")
  public StockDecreaseResponse prepareStock(@RequestBody StockPrepareRequest request) {
    List<ProductReservationInfoDomain> domain = request.toDomain();

    DecreaseStockResponse decreaseStockResponse = productInternalService.decreaseStock(domain);

    return StockDecreaseResponse.from(decreaseStockResponse);
  }

  @PostMapping("/rollback/{transactionId}")
  public StockIncreaseResponse rollbackStock(
      @PathVariable("transactionId") String transactionId) {

    return StockIncreaseResponse.from(productInternalService.increaseStock(transactionId));
  }

  @PostMapping("/commit/{transactionId}")
  public StockCommitResponse commitStock(
      @PathVariable("transactionId") String transactionId) {

    return StockCommitResponse.from(productInternalService.commitStock(transactionId));
  }
}
