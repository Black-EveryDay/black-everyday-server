package com.ed.orderservice.application.port.out;


import com.ed.OrderPaymentCreateRequestEvent;

public interface OrderPaymentCreateOutPort {
  void sendPaymentConfirmRequest(OrderPaymentCreateRequestEvent request);
}
