package com.ed.orderservice.infrastructure.message.kafka.payment;

import com.ed.OrderPaymentCreateRequestEvent;
import com.ed.orderservice.application.port.out.OrderEventStatusUpdateOutPort;
import com.ed.orderservice.infrastructure.message.kafka.config.Topics;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaymentCreateProducer {

  private final KafkaTemplate<String, OrderPaymentCreateRequestEvent> kafkaTemplate;
  private final OrderEventStatusUpdateOutPort orderEventStatusUpdateOutPort;

  @Transactional
  public void send(OrderPaymentCreateRequestEvent request) {
    try {
      kafkaTemplate.send(Topics.ORDER_PAYMENT_REQUEST, request.getOrderId(), request).get();
      orderEventStatusUpdateOutPort.updateToPaymentSuccess(request.getOrderId());
      log.info("Payment confirm request sent for order: {}", request.getOrderId());
    } catch (Exception ex) {
      orderEventStatusUpdateOutPort.updateToPaymentFailure(request.getOrderId());
      log.error("Failed to send payment confirm request for order: {}", request.getOrderId(), ex);
      throw new RuntimeException("Failed to send payment confirm request", ex);
    }
  }
}
