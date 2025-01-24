package com.ed.orderservice.infrastructure.message.kafka.adapter;

import com.ed.OrderPaymentCancelRequestEvent;
import com.ed.orderservice.application.port.out.OrderPaymentCancelOutPort;
import com.ed.orderservice.infrastructure.message.kafka.payment.OrderPaymentCancelProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPaymentCancelAdapter implements OrderPaymentCancelOutPort {
  private final OrderPaymentCancelProducer orderPaymentCancelProducer;

  @Override
  public void sendPaymentCancelRequest(OrderPaymentCancelRequestEvent request) {
    orderPaymentCancelProducer.send(request);
  }
}
