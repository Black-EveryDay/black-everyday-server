package com.ed.orderservice.presentaion.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ed.orderservice.application.port.out.response.NewPurchaseOrderMessageResponse;
import com.ed.orderservice.presentaion.port.in.CreateOrderUseCase;
import com.ed.orderservice.presentaion.web.request.NewOrderRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final CreateOrderUseCase createNewOrderUseCase;

	@PostMapping()
	public NewPurchaseOrderMessageResponse newOrder(
		@RequestBody @Valid NewOrderRequest request
	) {
		return NewPurchaseOrderMessageResponse.from(
			createNewOrderUseCase.createOrder(NewOrderRequest.toCommand(request))
		);
	}
}
