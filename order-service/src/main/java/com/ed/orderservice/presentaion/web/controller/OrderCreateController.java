package com.ed.orderservice.presentaion.web.controller;

import static com.ed.orderservice.libs.common.HttpHeaderConstants.HEADER_USER_ID;

import com.ed.orderservice.presentaion.port.in.CreateOrderUseCase;
import com.ed.orderservice.presentaion.web.request.NewOrderRequest;
import com.ed.orderservice.presentaion.web.response.domain.create.NewPurchaseOrderMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderCreateController {

  private final CreateOrderUseCase createNewOrderUseCase;

  @PostMapping("api/v1/orders")
  public NewPurchaseOrderMessageResponse newOrder(
      @RequestHeader(HEADER_USER_ID) String userUuid,
      @RequestBody NewOrderRequest request
  ) {
    return NewPurchaseOrderMessageResponse.from(
        createNewOrderUseCase.createOrder(NewOrderRequest.toCommand(request, userUuid))
    );
  }

}
