package com.ed.orderservice.infrastructure.message.kafka.payment;

import com.ed.OrderPaymentCancelRequestEvent;
import com.ed.orderservice.infrastructure.message.kafka.config.Topics;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaymentCancelProducer {

  private final KafkaTemplate<String, OrderPaymentCancelRequestEvent> kafkaTemplate;

  @Transactional
  public void send(OrderPaymentCancelRequestEvent request) {
    try {
      kafkaTemplate.send(Topics.ORDER_PAYMENT_CANCEL_REQUEST, request.getOrderId(), request).get();
      log.info("Payment confirm request sent for order: {}", request.getOrderId());
    } catch (Exception ex) {
      log.error("Failed to send payment confirm request for order: {}", request.getOrderId(), ex);
      throw new RuntimeException("Failed to send payment confirm request", ex);
    }
  }

}
