package com.ed.orderservice.infrastructure.message.kafka.payment;

import com.ed.OrderPaymentCreateRequest;
import com.ed.orderservice.infrastructure.message.kafka.config.Topics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaymentCreateProducer {
  private final KafkaTemplate<String, OrderPaymentCreateRequest> kafkaTemplate;

  public void send(OrderPaymentCreateRequest request) {
    try {
      kafkaTemplate.send(Topics.ORDER_PAYMENT_REQUEST, request.getOrderId(), request);
      log.info("Payment confirm request sent for order: {}", request.getOrderId());
    } catch (Exception e) {
      log.error("Failed to send payment confirm request for order: {}", request.getOrderId(), e);
      throw new RuntimeException("Failed to send payment confirm request", e);
    }
  }
}


