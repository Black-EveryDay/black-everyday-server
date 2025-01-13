package com.ed.orderservice.application.port.out;


import com.ed.OrderPaymentCreateRequest;

public interface OrderPaymentCreateOutPort {
  void sendPaymentConfirmRequest(OrderPaymentCreateRequest request);
}
