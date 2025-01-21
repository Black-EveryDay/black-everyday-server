package com.ed.orderservice.infrastructure.message.kafka.adapter;

import com.ed.OrderPaymentCreateRequestEvent;
import com.ed.orderservice.application.port.out.OrderPaymentCreateOutPort;
import com.ed.orderservice.infrastructure.message.kafka.payment.OrderPaymentCreateProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPaymentCreateAdapter implements OrderPaymentCreateOutPort {
  private final OrderPaymentCreateProducer orderPaymentCreateProducer;

  @Override
  public void sendPaymentConfirmRequest(OrderPaymentCreateRequestEvent request) {
    orderPaymentCreateProducer.send(request);
  }
}
